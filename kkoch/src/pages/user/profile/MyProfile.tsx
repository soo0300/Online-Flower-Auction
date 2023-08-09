import {
  MDBCol,
  MDBRow,
  MDBCard,
  MDBCardText,
  MDBCardBody,
  MDBCardImage,
  MDBBtn,
} from 'mdb-react-ui-kit';
import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import secureLocalStorage from 'react-secure-storage';
import "./btn.css"

interface pwChangeForm {
  newPw: string;
  newPwCheck: string;
}

const MyProfile = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [tel, setTel] = useState('');
  const [bn, setBn] = useState('');

  const [showChangePassword, setShowChangePassword] = useState(false);
  const [newPw, setNewPw] = useState("");
  const [newPwCheck, setNewPwCheck] = useState("");
	const [newPwValid, setNewPwValid ] = useState(false);
  const [newPwCheckValid, setNewPwCheckValid ] = useState(false);
	const [ notAllow, setNotAllow ] = useState(true);


	useEffect(() => {
    if (newPwValid && newPwCheckValid) {
      setNotAllow(false);
			return;
		}
		setNotAllow(true);
	}, [newPwValid, newPwCheckValid]);
  
  // 비밀번호 검증 함수
  const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newPw = e.target.value;
    setNewPw(newPw);
    
    const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
    if (regex.test(newPw)) {
      setNewPwValid(true);
    } else {
      setNewPwValid(false);
    }
  }

  // 비밀번호 확인 검증 함수
  const handlePasswordCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newPwCheck = e.target.value;
    setNewPwCheck(newPwCheck);
    
    const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
    if (regex.test(newPwCheck)) {
      setNewPwCheckValid(true);
    } else {
      setNewPwCheckValid(false);
    }
  }

  const {
    register, // input 요소를 react-hook-form과 연결시켜 검증 규칙을 적용하는 메서드
    formState: { errors }, // form state 정보 담는 객체
    handleSubmit, // form을 제출할 때 실행할 함수.
    setError, // error 관련 설정 함수
  } = useForm<pwChangeForm>({ mode: 'onBlur' }) // onBlur 이벤트 발생할 때 validation 실행

  // submitHandler
  const onValid = (data: pwChangeForm) => {
    if (data.newPw !== data.newPwCheck) {
      setError(
        'newPwCheck',
        { message: '비밀번호가 일치하지 않습니다.' },
        { shouldFocus: true },
      );
    }
  }
  
  useEffect(() => {
    axios({
      method: 'get',
      // url: `https://i9c204.p.ssafy.io/api/user-service/${secureLocalStorage.getItem("memberkey")}`,
      url: `/api/api/user-service/${secureLocalStorage.getItem("memberkey")}`,
      headers: {
        Authorization: `Bearer ${secureLocalStorage.getItem("token")}`
      }
    })
    .then((res) => {
      setUsername(res.data.data["name"]);
      setEmail(res.data.data["email"]);
      setTel(res.data.data["tel"]);
      setBn(res.data.data["businessNumber"]);
    })
  }, [])

  const handlePasswordChange = () => {
    console.log(newPw);
    console.log(notAllow)
    // 여기서 newPassword와 confirmPassword를 사용하여 비밀번호 변경 로직을 구현합니다.
    // 비밀번호 변경 요청을 보내고 성공적으로 처리되면 상태를 초기화하거나 다른 작업을 수행합니다.
    // 비밀번호 변경이 성공적으로 완료되면 setShowChangePassword(false)를 호출하여 폼을 숨깁니다.
    // 비밀번호 변경이 실패하는 경우 오류 처리를 추가할 수 있습니다.
    // 비밀번호 변경 후 작업을 위해 axios 요청 예시:
    /*
    axios({
      method: "put",
      url: `/api/api/user-service/${secureLocalStorage.getItem("memberkey")}/password`,
      headers: {
        Authorization: `Bearer ${secureLocalStorage.getItem("token")}`,
      },
      data: {
        newPassword: newPassword,
        confirmPassword: confirmPassword,
      },
    })
      .then((res) => {
        // 비밀번호 변경 성공 처리
        setShowChangePassword(false);
        setNewPassword("");
        setConfirmPassword("");
      })
      .catch((err) => {
        // 비밀번호 변경 실패 처리
        console.log(err);
      });
    */
  };
  return (
    <>
      <MDBRow>
        <MDBCol lg="4">
          <MDBCard className="mb-4">
            <MDBCardBody className="text-center flex flex-col items-center justify-center">
              <MDBCardImage
                src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp"
                alt="profile"
                className="rounded-circle w-[150px] mb-3"
                fluid />
              <p className="text-muted mb-1">Full Stack Developer</p>
              <p className="text-muted mb-4">Bay Area, San Francisco, CA</p>
              <div className="mb-2">
                <MDBBtn>프로필 변경</MDBBtn>
              </div>
            </MDBCardBody>
          </MDBCard>
        </MDBCol>

        <MDBCol lg="8">
          <MDBCard className="mb-4">
            <MDBCardBody>
              <MDBRow>
                <MDBCol sm="3">
                  <MDBCardText>이름</MDBCardText>
                </MDBCol>
                <MDBCol sm="9">
                  <MDBCardText className="text-muted">{username}</MDBCardText>
                </MDBCol>
              </MDBRow>
              <hr />
              <MDBRow>
                <MDBCol sm="3">
                  <MDBCardText>Email</MDBCardText>
                </MDBCol>
                <MDBCol sm="9">
                  <MDBCardText className="text-muted">{email}</MDBCardText>
                </MDBCol>
              </MDBRow>
              <hr />
              <MDBRow>
                <MDBCol sm="3">
                  <MDBCardText>Phone</MDBCardText>
                </MDBCol>
                <MDBCol sm="9">
                  <MDBCardText className="text-muted">{tel}</MDBCardText>
                </MDBCol>
              </MDBRow>
              <hr />
              <MDBRow>
                <MDBCol sm="3">
                  <MDBCardText>Mobile</MDBCardText>
                </MDBCol>
                <MDBCol sm="9">
                  <MDBCardText className="text-muted">{bn}</MDBCardText>
                </MDBCol>
              </MDBRow>
              <hr />
              <MDBRow>
                <MDBCol sm="3">
                  <MDBCardText>비밀번호 변경</MDBCardText>
                </MDBCol>
                <MDBCol sm="9">
                {showChangePassword ? (
                  <form className='border flex flex-col' onSubmit={handleSubmit(onValid)}>
                    <input
                      type="password"
                      {...register('newPw', {
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
                      {/* onChange={(e) => setNewPassword(e.target.value)} */}
                    <div className=''>
                      {
                        !newPwCheckValid && newPwCheck.length > 0 && (
                          <div>
                            {errors?.newPwCheck?.message}
                          </div>
                        )
                      }
                    </div>
                    <input
                      type="password"
                      {...register('newPwCheck', {
                        required: '비밀번호를 입력해주세요.',
                        minLength: {
                          value: 8,
                          message: '비밀번호는 숫자, 영문 대소문자, 특수문자를 포함한 8글자 이상 입력해주세요.',
                        },
                      })}
                      onChange={ handlePasswordCheck }
                      placeholder='비밀번호를 한 번 더 입력해주세요'
                    />
                    <div className=''>
                      {
                        !newPwCheckValid && newPwCheck.length > 0 && (
                          <div>
                            {errors?.newPwCheck?.message}
                          </div>
                        )
                      }
                    </div>
                    <div className='flex justify-end mt-2'>
                      <button className="btn" onClick={handlePasswordChange} disabled={notAllow}>
                        변경하기
                      </button>
                      <button className="btn" onClick={() => setShowChangePassword(false)}>
                        취소
                      </button>
                    </div>
                  </form>
                ) : (
                  <MDBCardText className="text-muted">
                    <button className='btn' onClick={() => setShowChangePassword(true)}>
                      비밀 번호 변경하기
                    </button>
                  </MDBCardText>
                )}
                </MDBCol>
              </MDBRow>
            </MDBCardBody>
          </MDBCard>
        </MDBCol>
        
      </MDBRow>
    </>
  )
}

export default MyProfile