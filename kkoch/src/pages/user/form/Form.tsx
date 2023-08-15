import React from "react";
import * as Components from "./Components";
import SignUp from "./SignUp";
import Login from "./Login";
import { useDispatch } from "react-redux";
import { setEmail } from "@/reducer/store/authSlice";

const Form = ({props}) => {
  const [signIn, toggle] = React.useState(props);
  const dispatch = useDispatch();

  const handleSignUpSuccess = (email) => {
    toggle(true);
    dispatch(setEmail(email));
  }


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
        <SignUp onSignUpSuccess={handleSignUpSuccess}/>
      </Components.SignUpContainer>
      <Components.SignInContainer signingIn={signIn}>
        <Login/>
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
            <Components.Box>
              <Components.Title>이미 회원이신가요?</Components.Title>
              {/* <Components.Paragraph>
                <p>asdf</p>
              </Components.Paragraph> */}
              <Components.GhostButton onClick={() => toggle(true)}>
                로그인
              </Components.GhostButton>
            </Components.Box>
          </Components.LeftOverlayPanel>
          <Components.RightOverlayPanel signingIn={signIn}>
          <Components.Box>
            <Components.Title>계정이 없으신가요?</Components.Title>
            {/* <Components.Paragraph>
              회원 가입 하러 가기
            </Components.Paragraph> */}
            <Components.GhostButton onClick={() => toggle(false)}>
              회원 가입
            </Components.GhostButton>
          </Components.Box>
          </Components.RightOverlayPanel>
        </Components.Overlay>
      </Components.OverlayContainer>
    </Components.Container>
  );
}

export default Form;


