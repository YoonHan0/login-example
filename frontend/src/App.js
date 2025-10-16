import React, {useEffect} from 'react';
import SignupForm from './components/SignupForm';
import LoginForm from './components/LoginForm';

function App() {

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const access = params.get('accessToken');
    const refresh = params.get('refreshToken');
    if (access) localStorage.setItem('access', access);
    if (refresh) localStorage.setItem('refresh', refresh);
  }, []);

  const handleSocialLogin = (flag) => {
    if(flag === 'google') {
      window.location.href = '/oauth2/authorization/google';    // http://localhost:8080/oauth2/authorization/google, 개발 서버 proxy가 http://localhost:8080로 되어 있다면 상대 주소 그대로 동작
    }
    else {
      window.location.href = "/oauth2/authorization/kakao"
    }
    
  };
  
  return (
    <div style={{padding:20}}>
      <h1>Login Example</h1>
      <SignupForm />
      <hr />
      <LoginForm />
      <hr />
      <div>
        <button onClick={handleSocialLogin}>Login with Google</button>
        <br/>
        <button onClick={handleSocialLogin}>Login with KaKao</button>
      </div>
    </div>
  );
}
export default App;