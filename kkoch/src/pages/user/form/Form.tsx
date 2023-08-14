import React, { useEffect, useState } from "react";
import { useLocation } from "react-router";
import * as Components from "./Components";
import SignUp from "./SignUp";
import Login from "./Login";

const Form = ({props}) => {
  const [signIn, toggle] = React.useState(props);
  const { state } = useLocation();	// 이전 라우터 위치정보를 가져온다
  
  const [email, setEmail] = useState(state && state.email ? state.email : '');

  useEffect(() => {
    setEmail(email)
  }, [email, state])

  return (
    <Components.Container>
      <Components.SignUpContainer signingIn={signIn}>
        {/* <Components.Form>
          <Components.Title>회원가입</Components.Title>
          <Components.Input type="text" placeholder="Name" />
          <Components.Input type="email" placeholder="Email" />
          <Components.Input type="password" placeholder="Password" />
          <Components.Button>회원가입</Components.Button>
        </Components.Form> */}
        <SignUp/>
      </Components.SignUpContainer>
      <Components.SignInContainer signingIn={signIn}>
        <Login propsEmail={email}/>
        {/* <Components.Form>
          <Components.Title>로그인</Components.Title>
          <Components.Input type="email" placeholder="Email" />
          <Components.Input type="password" placeholder="Password" />
          <Components.Anchor href="#">Forgot your password?</Components.Anchor>
          <Components.Button>로그인 하기</Components.Button>
        </Components.Form> */}
      </Components.SignInContainer>
      <Components.OverlayContainer signingIn={signIn}>
        <Components.Overlay signingIn={signIn}>
          <Components.LeftOverlayPanel signingIn={signIn}>
            <Components.Title>이미 회원이신가요?</Components.Title>
            <Components.Paragraph>
              <p>asdf</p>
            </Components.Paragraph>
            <Components.GhostButton onClick={() => toggle(true)}>
              로그인
            </Components.GhostButton>
          </Components.LeftOverlayPanel>
          <Components.RightOverlayPanel signingIn={signIn}>
            <Components.Title>계정이 없으신가요?</Components.Title>
            <Components.Paragraph>
              회원 가입 하러 가기
            </Components.Paragraph>
            <Components.GhostButton onClick={() => toggle(false)}>
              회원 가입
            </Components.GhostButton>
          </Components.RightOverlayPanel>
        </Components.Overlay>
      </Components.OverlayContainer>
    </Components.Container>
  );
}

export default Form;


