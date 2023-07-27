import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import './SignUp.css';

interface ISignUpForm {
  name: string;
  email: string;
  pw: string;
  pwCheck: string;
  businessNumber: string;
  extraError: string;
}

const SignUp = () => {
  const [ name, setName ] = useState('');
	const [ email, setEmail ] = useState('');
	const [ businessNumber, setBusinessNumber ] = useState('');
	const [ pw, setPw] = useState('');
	const [ pwCheck, setPwCheck] = useState('');

	const [ nameValid, setNameValid ] = useState(false);
	const [ emailValid, setEmailValid ] = useState(false);
	const [ businessNumberValid, setBusinessNumberValid ] = useState(false);
	const [ pwValid, setPwValid ] = useState(false);
	const [ pwCheckValid, setPwCheckValid ] = useState(false);
	const [ notAllow, setNotAllow ] = useState(true);

  // 이름 검증 함수
  const handleName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);

    const regex = /^[A-za-z0-9가-힣]{2,30}$/
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
    setPw(e.target.value);
    
		const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
		if (regex.test(pw)) {
      setPwValid(true);
		} else {
      setPwValid(false);
		}
	}
  
  // 비밀번호 확인 검증 함수
	const handlePasswordCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPwCheck(e.target.value);
    
		const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
		if (regex.test(pwCheck)) {
      setPwCheckValid(true);
		} else {
      setPwCheckValid(false);
		}
	}
  
  // 회원가입 버튼
  const onClickConfirmButton = () => {
    if (name && email && businessNumber && pw && pwCheck) {
      alert('회원가입 성공')
    } else {
      alert('다시 확인해주세요.')
    }
  }
  
	useEffect(() => {
    if (nameValid && emailValid && businessNumberValid && pwValid && pwCheckValid) {
      setNotAllow(false);
			return;
		}
		setNotAllow(true);
	}, [nameValid, emailValid, businessNumberValid, pwValid, pwCheckValid]);
  
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
    <form onSubmit={handleSubmit(onValid)}>
      <fieldset className='gap-16 bg-gray-20 py-10 md:h-full md:pb-0'>

        <legend style={{ display: 'none' }}>회원가입</legend>
        <div className="titleWrap">
          회원가입
        </div>

        <div className='signupTitle'>
          이미 계정이 있으신가요?
          <div className='signupLink'>
            로그인
          </div>
        </div>

        {/* 이름 */}
        <div className='inputTitle'>이름</div>
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

        {/* 사업자 등록 번호 */}
        <div className='inputTitle'>사업자 등록 번호</div>
        <div className='inputWrap'>
          {/* <LockIcon /> 아이콘 */}
          <input
            className='input'
            {...register('businessNumber', {
              required: '사업자등록번호를 입력해주세요.',
              pattern: {
                value: /([0-9]{3})-?([0-9]{2})-?([0-9]{5})/,
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
      </fieldset>
      <button onClick={ onClickConfirmButton } className='bottomButton' disabled={ notAllow }>
				확인
			</button>
      {/* {errors?.extraError?.message && <p>{errors?.extraError?.message}</p>} */}
    </form>
  );
};

export default SignUp