package com.kkoch.auction.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>(); //웹소켓 세션 store
    private static String videoSessionId = null; //비디오 세션
    private static String adminSession = null; //관리자 세션 -> 최초 소켓 생성자

    //입장
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("WebSocketHandler#afterConnectionEstablished");
        log.info("session id = {}", session.getId());

        //방 생성자 외의 참가자는 videoSessionId 반환
        if (!sessions.isEmpty()) {
            session.sendMessage(new TextMessage(videoSessionId));
        }

        //web socket 세션 저장
        sessions.put(session.getId(), session);
    }

    //통신
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("WebSocketHandler#handleTextMessage");
        log.info("session id = {}", session.getId());
        log.info("message = {}", message);

        String currentSessionId = session.getId();

        //json 파싱
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(message.getPayload());

        //json 객체 변환
        JSONObject json = (JSONObject) obj;

        //권한 확인 추출
        String role = (String) json.get("role");
        log.info("role = {}", role);

        //고객인데 경매방이 열리지 않은 경우
        if (isClient(role) && isNotOpenAuction()) {
            //세션 제거
            sessions.remove(currentSessionId);
            session.sendMessage(new TextMessage("not start auction"));
            return;
        }

        //관리자인 경우에만 videoSessionId 저장
        if (isAdmin(role)) {
            adminSession = currentSessionId;
            videoSessionId = (String) json.get("sessionId");
            log.info("adminSession={}, videoSessionId={}", adminSession, videoSessionId);
        }

        sessions.forEach((key, value) -> {
            if (!key.equals(currentSessionId)) {
                try {
                    value.sendMessage(new TextMessage(videoSessionId));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //퇴장
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocketHandler#afterConnectionClosed");
        log.info("session id = {}", session.getId());

        String currentSessionId = session.getId();

        //세션 저장소에서 현재 세션 제거
        sessions.remove(currentSessionId);

        //현재 세션이 관리자 세션이라면 모든 세션 종료
        if (currentSessionId.equals(adminSession)) {
            //관리자 세션 초기화
            adminSession = null;
            //종료 메세지 출력
            sendEndMessage();
            //모든 세션 종료
            sessions.clear();
        }

        //세션이 비어 있는 경우
        if (sessions.isEmpty()) {
            //비디오 세션 초기화
            videoSessionId = null;
        }
    }

    private boolean isClient(String role) {
        return role.equals("client");
    }

    private boolean isAdmin(String role) {
        return role.equals("admin");
    }

    private boolean isNotOpenAuction() {
        return videoSessionId == null;
    }

    private void sendEndMessage() {
        sessions.forEach((key, value) -> {
            try {
                value.sendMessage(new TextMessage("금일 경매가 종료되었습니다."));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
