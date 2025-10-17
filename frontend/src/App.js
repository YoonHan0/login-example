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
      window.location.href = 'http://localhost:8080/oauth2/authorization/google';
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
        <button onClick={() => handleSocialLogin('google')}>Login with Google</button>
        <br/>
        <button onClick={() => handleSocialLogin('kakao')}>Login with KaKao</button>
      </div>
    </div>
  );
}
export default App;