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
  
  return (
    <div style={{padding:20}}>
      <h1>Auth Example</h1>
      <SignupForm />
      <hr />
      <LoginForm />
      <hr />
      <div>
        <a href="/oauth2/authorization/google">Login with Google</a>
        <br/>
        <a href="/oauth2/authorization/kakao">Login with Kakao</a>
      </div>
    </div>
  );
}
export default App;