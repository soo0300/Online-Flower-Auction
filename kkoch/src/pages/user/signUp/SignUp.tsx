import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import './SignUp.css';
import flowerImg from "@/assets/flowerBackImg.png"
import axios from "axios";
import { useNavigate } from 'react-router-dom';

interface ISignUpForm {
  name: string;
  email: string;
  pw: string;
  pwCheck: string;
  businessNumber: string;
  tel: string;
  extraError: string;
}

const SignUp = () => {
  const [ name, setName ] = useState('');
	const [ email, setEmail ] = useState('');
	const [ businessNumber, setBusinessNumber ] = useState('');
	const [ pw, setPw] = useState('');
	const [ pwCheck, setPwCheck] = useState('');
	const [ tel, setTel] = useState('');
  const [ file, setFile ] = useState('');

	const [ nameValid, setNameValid ] = useState(false);
	const [ emailValid, setEmailValid ] = useState(false);
	const [ businessNumberValid, setBusinessNumberValid ] = useState(false);
	const [ telValid, setTelValid ] = useState(false);
	const [ pwValid, setPwValid ] = useState(false);
	const [ pwCheckValid, setPwCheckValid ] = useState(false);
	const [ notAllow, setNotAllow ] = useState(true);

  const navigate = useNavigate();

  // 이름 검증 함수
  const handleName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newName = e.target.value;
    setName(newName);

    const regex = /^[A-za-z0-9가-힣]{2,30}$/
    if (regex.test(newName)) {
      setNameValid(true);
    } else {
      setNameValid(false);
    }
  }
  
	// 이메일 검증 함수
	const handleEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newEmail = e.target.value;
    setEmail(newEmail);

		// 이메일 정규표현식
		const regex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
    
		// 정규표현식이 true 이면
		if (regex.test(newEmail)) {
      setEmailValid(true);
		} else {
      setEmailValid(false);
		}
	}
  
  // 사업자 번호 검증 함수
	const handleBusinessNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newBN = e.target.value;
		setBusinessNumber(newBN);
    
		// 사업자 번호 정규표현식
		const regex = /^([0-9]{3})-?([0-9]{2})-?([0-9]{5})$/
    
		// 정규표현식이 true 이면
		if (regex.test(newBN)) {
      setBusinessNumberValid(true);
		} else {
      setBusinessNumberValid(false);
		}
	}

  // 전화 번호 검증 함수
	const handleTel = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newTel = e.target.value;
    setTel(newTel);
  
		// 전화 번호 정규표현식
		const regex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/

		// 정규표현식이 true 이면
		if (regex.test(newTel)) {
      setTelValid(true);
		} else {
      setTelValid(false);
		}
	}
  
	// 비밀번호 검증 함수
	const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newPw = e.target.value;
    setPw(newPw);
    
		const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
		if (regex.test(newPw)) {
      setPwValid(true);
		} else {
      setPwValid(false);
		}
	}
  
  // 비밀번호 확인 검증 함수
	const handlePasswordCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newPwCheck = e.target.value;
    setPwCheck(newPwCheck);
    
		const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
		if (regex.test(newPwCheck)) {
      setPwCheckValid(true);
		} else {
      setPwCheckValid(false);
		}
	}

  const fileInput = React.useRef(null);
  
  
  const handleChangeFile = (event) => {
    setFile(event.target.files[0])
  }

  // 회원가입 버튼
  const onClickConfirmButton = (e: { preventDefault: () => void; }) => {
    e.preventDefault();

    const formData = new FormData()
    
    formData.append("file", file) //files[0] === upload file
  
    const dto = {
      email: email,
      loginPw: pw,
      name: name,
      tel: tel,
      businessNumber: businessNumber
    }

    const blob = new Blob([JSON.stringify(dto)], {type: "application/json"}) 
    formData.append("dto", blob)

    axios({
			method: "post",
			url: "https://i9c204.p.ssafy.io/api/user-service/join", // 프록시 경로인 /api를 사용
			// url: "/api/api/user-service/join", // 프록시 경로인 /api를 사용
			headers: {
				"Content-Type": "application/json"
			},
			data: dto
		})
		.then(() => { 
			if(confirm("회원 가입이 완료 되었습니다. 로그인 해주십시오")) {
        navigate("/login", { state: { email: email } });
      }

		})
		.catch((err) => {
			console.log(err.response.data)
      alert("회원가입에 실패 했습니다")
		});
  }
  
	useEffect(() => {
    if (nameValid && emailValid && businessNumberValid && telValid && pwValid && pwCheckValid) {
      setNotAllow(false);
			return;
		}
		setNotAllow(true);
	}, [nameValid, emailValid, businessNumberValid, telValid, pwValid, pwCheckValid]);
  
  const {
    register, // input 요소를 react-hook-form과 연결시켜 검증 규칙을 적용하는 메서드
    formState: { errors }, // form state 정보 담는 객체
    handleSubmit, // form을 제출할 때 실행할 함수.
    setError, // error 관련 설정 함수
  } = useForm<ISignUpForm>({ mode: 'onBlur' }) // onBlur 이벤트 발생할 때 validation 실행

  // submitHandler
  const onValid = (data: ISignUpForm) => {
    if (data.pw !== data.pwCheck) {
      setError(
        'pwCheck',
        { message: '비밀번호가 일치하지 않습니다.' },
        { shouldFocus: true },
      );
    }
  }

  return (
		<div className="flex justify-around mt-[30px]">
			<div className='mt-[100px]'>
				<img src={flowerImg} alt="" />
			</div>
      <form className='w-[35%] flex flex-col' onSubmit={handleSubmit(onValid)}>
        <fieldset>
          <legend style={{ display: 'none' }}>회원가입</legend>
          <div className="text-3xl font-bold mt-3">
            회원가입
          </div>

          <div className='signupTitle cursor-pointer' onClick={() => navigate("/login")}>
            이미 계정이 있으신가요? &nbsp;&nbsp;
            <span className='signupLink'>
              로그인
            </span>
          </div>

          {/* 이름 */}
          <div className='inputTitle mt-3'>이름</div>
          <div className='inputWrap'>
            {/* <LockIcon /> 아이콘 */}
            <input 
              className='input'
              {...register('name', {
                required: '이름을 입력해주세요.',
                minLength: {
                  value: 2,
                  message: '2글자 이상 입력해주세요.',
                },
                pattern: {
                  value: /^[A-za-z0-9가-힣]{2,30}$/,
                  message: '가능한 문자: 영문 대소문자, 글자 단위 한글, 숫자',
                },
              })}
              placeholder='이름을 입력해주세요'
              onChange={handleName}
            />
          </div>
          <div className='errorMessageWrap'>
            {
              !nameValid && name.length > 0 && (
                <div>
                  {errors?.name?.message}
                </div>
              )
            }
          </div>
          
          {/* 이메일 */}
          <div className='inputTitle'>이메일</div>
          <div className='inputWrap'>
            {/* <LockIcon /> 아이콘 */}
            <input
              className='input'
              {...register('email', {
                required: '이메일을 입력해주세요.',
                pattern: {
                  value: /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i,
                  message: '이메일을 올바르게 입력해주세요.',
                },
              })}
              onChange={handleEmail} 
              placeholder='이메일을 입력해주세요'
            />
          </div>
          <div className='errorMessageWrap'>
            {
              !emailValid && email.length > 0 && (
                <div>
                  {errors?.email?.message}
                </div>
              )
            }
          </div>

          {/* 비밀번호 */}
          <div className='inputTitle'>비밀번호</div>
          <div className='inputWrap'>
            {/* <LockIcon /> 아이콘 */}
            <input
              className='input'
              type="password"
              {...register('pw', {
                required: '비밀번호를 입력해주세요.',
                minLength: {
                  value: 8,
                  message: '비밀번호는 숫자, 영문 대소문자, 특수문자를 포함한 8글자 이상 입력해주세요.',
                },
                pattern: {
                  value: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
                  message: '비밀번호는 숫자, 영문 대소문자, 특수문자를 포함한 8글자 이상 입력해주세요.',
                },
              })}
              placeholder='비밀번호를 입력해주세요'
              onChange={ handlePassword }
            />
          </div>
          <div className='errorMessageWrap'>
            {
              !pwValid && pw.length > 0 && (
                <div>
                  {errors?.pw?.message}
                </div>
              )
            }
          </div>

          {/* 비밀번호 확인 */}
          <div className='inputTitle'>비밀번호 확인</div>
          <div className='inputWrap'>
            {/* <LockIcon /> 아이콘 */}
            <input
              className='input'
              type="password"
              {...register('pwCheck', {
                required: '비밀번호를 입력해주세요.',
                minLength: {
                  value: 8,
                  message: '비밀번호는 숫자, 영문 대소문자, 특수문자를 포함한 8글자 이상 입력해주세요.',
                },
              })}
              onChange={ handlePasswordCheck }
              placeholder='비밀번호를 한 번 더 입력해주세요'
            />
          </div>
          <div className='errorMessageWrap'>
            {
              !pwCheckValid && pwCheck.length > 0 && (
                <div>
                  {errors?.pwCheck?.message}
                </div>
              )
            }
          </div>

          {/* 사업자 등록 번호 */}
          <div className='inputTitle'>사업자 등록 번호</div>
          <div className='inputWrap'>
            {/* <LockIcon /> 아이콘 */}
            <input
              className='input'
              type='text'
              {...register('businessNumber', {
                required: '사업자등록번호를 입력해주세요.',
                pattern: {
                  value: /^([0-9]{3})-?([0-9]{2})-?([0-9]{5})$/,
                  message: '사업자등록번호를 올바르게 입력해주세요.',
                },
              })}
              onChange={handleBusinessNumber} 
              placeholder='사업자등록번호를 입력해주세요'
            />
          </div>
          <div className='errorMessageWrap'>
            {
              !businessNumberValid && businessNumber.length > 0 && (
                <div>
                  {errors?.businessNumber?.message}
                </div>
              )
            }
          </div>

          {/* 전화 번호 */}
          <div className='inputTitle'>전화 번호</div>
          <div className='inputWrap'>
            {/* <LockIcon /> 아이콘 */}
            <input
              className='input'
              {...register('tel', {
                required: '전화번호를 입력해주세요.',
                pattern: {
                  value: /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/,
                  message: '전화번호를 올바르게 입력해주세요.',
                },
              })}
              onChange={handleTel} 
              placeholder='전화번호를 입력해주세요'
            />
          </div>
          <div className='errorMessageWrap'>
            {
              !telValid && tel.length > 0 && (
                <div>
                  {errors?.tel?.message}
                </div>
              )
            }
          </div>
          
          {/* 파일 업로드 확인 */}
          <div className='inputTitle'>사업자 등록증 첨부</div>
          <div className='inputWrap'>
            {/* <LockIcon /> 아이콘 */}
            <input 
              type="file" 
              ref={fileInput}
              onChange={handleChangeFile}/>
          </div>

        </fieldset>
        
        <button onClick={ onClickConfirmButton } className='bottomButton mt-3 bg-orange-400' disabled={ notAllow }>
          회원 가입
        </button>
      </form>
    </div>
  );
};

export default SignUp