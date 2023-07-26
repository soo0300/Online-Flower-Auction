import React, { useEffect, useState } from 'react';
import './SignUp.css';

// type Props = {}

const SignUp = () => {
  const [ name, setName ] = useState('');
	const [ email, setEmail ] = useState('');
	const [ businessNumber, setBusinessNumber ] = useState('');
	const [ password, setPassword] = useState('');
	const [ passwordCheck, setPasswordCheck] = useState('');

	const [ nameValid, setNameValid ] = useState(false);
	const [ emailValid, setEmailValid ] = useState(false);
	const [ businessNumberValid, setBusinessNumberValid ] = useState(false);
	const [ passwordValid, setPasswordValid ] = useState(false);
	const [ passwordCheckValid, setPasswordCheckValid ] = useState(false);
	const [ notAllow, setNotAllow ] = useState(true);

  // 이름 검증 함수
  const handleName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);

    const regex =  /^[가-힣]{2,4}|[a-zA-Z]{2,10}\s[a-zA-Z]{2,10}$/
    if (regex.test(name)) {
      setNameValid(true);
    } else {
      setNameValid(false);
    }

  }


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

  // 사업자 번호 검증 함수
	const handleBusinessNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
		setBusinessNumber(e.target.value);

		// 사업자 번호 정규표현식
		const regex = /([0-9]{3})-?([0-9]{2})-?([0-9]{5})/

		// 정규표현식이 true 이면
		if (regex.test(businessNumber)) {
			setBusinessNumberValid(true);
		} else {
			setBusinessNumberValid(false);
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

  // 비밀번호 확인 검증 함수
	const handlePasswordCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
		setPasswordCheck(e.target.value);

		const regex = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,20}$/;
		if (regex.test(passwordCheck)) {
			setPasswordCheckValid(true);
		} else {
			setPasswordCheckValid(false);
		}
	}


	useEffect(() => {
		if (nameValid && emailValid && businessNumberValid && passwordValid && passwordCheckValid) {
			setNotAllow(false);
			return;
		}
		setNotAllow(true);
	}, [nameValid, emailValid, businessNumberValid, passwordValid, passwordCheckValid]);

  return (
		<div className="page">
		<div className="titleWrap">
			로그인
		</div>

		<div className='loginTitle'>
			이미 계정이 있으신가요?
			<div className='loginLink'>
				로그인
			</div>
		</div>

		<div className="contentWrap">
      <div className="inputTitle">이름</div>
        <div className="inputWrap">
          <input 
            type='text'
            className="input"
            value={name}
            onChange={handleName} 
          />
        </div>
        <div className="errorMessageWrap">
          {
            !nameValid && name.length > 0 && (
              <div>
                올바른 이름을 입력해주세요.
              </div>
            )
          }
			</div>

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

      <div className="inputTitle">사업자 번호</div>
        <div className="inputWrap">
          <input 
            type='text'
            className="input"
            value={businessNumber}
            onChange={handleBusinessNumber} 
          />
        </div>
        <div className="errorMessageWrap">
          {
            !businessNumberValid && businessNumber.length > 0 && (
              <div>
                올바른 사업자 번호를 입력해주세요.
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

      <div className="inputTitle" style={{ marginTop: "26px" }}>비밀번호 확인</div>
			<div className="inputWrap">
				<input
					type="password"
					className="input"
					placeholder="비밀번호를 한 번 더 입력해주세요." 
					value={ passwordCheck }
					onChange={ handlePasswordCheck }
				/>
			</div>
			<div className="errorMessageWrap">
				{
					!passwordCheckValid && passwordCheck.length > 0 && (
						<div>
							비밀번호를 한 번 더 입력해주세요.
						</div>
					)
				}
			</div>
		</div>
		
		<div>
			<button className='bottomButton' disabled={ notAllow }>
				확인
			</button>
		</div>
	</div>
  )
}

export default SignUp
