import React, { useEffect, useState } from 'react';
import './Login.css';

// type Props = {}

const User = {
	email: 'test@naver.com',
	password: 'test2323@@@'
}

const Login = () => {
	const [ email, setEmail ] = useState('');
	const [ password, setPassword] = useState('');

	const [ emailValid, setEmailValid ] = useState(false);
	const [ passwordValid, setPasswordValid ] = useState(false);
	const [ notAllow, setNotAllow ] = useState(true);

	// 이메일 검증 함수
	const handleEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
		setEmail(e.target.value);

		// 이메일 정규표현식
		const regex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i

		// 정규표현식이 true 이면
		if (regex.test(email)) {
			setEmailValid(true);
		} else {
			setEmailValid(false);
		}

	}

	// 비밀번호 검증 함수
	const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
		setPassword(e.target.value);

		const regex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;
		if (regex.test(password)) {
			setPasswordValid(true);
		} else {
			setPasswordValid(false);
		}
	}

	const onClickConfirmButton = () => {
		if (email === User.email && password === User.password) {
			alert('로그인 성공')
		} else {
			alert('등록되지 않은 회원입니다.')
		}
	}

	useEffect(() => {
		if (emailValid && passwordValid) {
			setNotAllow(false);
			return;
		}
		setNotAllow(true);
	}, [emailValid, passwordValid]);

  return (
		<div className="page">
		<div className="titleWrap">
			로그인
		</div>

		<div className="contentWrap">
			<div className="inputTitle">이메일 주소</div>
			<div className="inputWrap">
				<input 
					type='text'
					className="input"
					placeholder='test@naver.com'
					value={email}
					onChange={handleEmail} 
				/>
			</div>
			<div className="errorMessageWrap">
				{
					!emailValid && email.length > 0 && (
						<div>
							올바른 이메일을 입력해주세요.
						</div>
					)
				}
			</div>

			<div className="inputTitle" style={{ marginTop: "26px" }}>비밀번호</div>
			<div className="inputWrap">
				<input
					type="password"
					className="input"
					placeholder="영문, 숫자, 특수문자 포함 8자 이상" 
					value={ password }
					onChange={ handlePassword }
				/>
			</div>
			<div className="errorMessageWrap">
				{
					!passwordValid && password.length > 0 && (
						<div>
							영문, 숫자, 특수문자 포함 8자 이상 입력해주세요.
						</div>
					)
				}
			</div>
		</div>
		
		<div>
			<button onClick={ onClickConfirmButton } className='bottomButton' disabled={ notAllow }>
				확인
			</button>
		</div>
	</div>
  )
}

export default Login
