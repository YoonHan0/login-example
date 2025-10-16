import React, {useState} from 'react';

export default function LoginForm() {

  const [email,setEmail] = useState('');
  const [pw,setPw] = useState('');
  const login = async () => {

    const res = await fetch(
      '/api/auth/login',
      {
        method:'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({
          email,
          password: pw
        })
      }
    );

    if(res.ok) { 
      const body = await res.json(); 
      localStorage.setItem('access', body.accessToken); 
      localStorage.setItem('refresh', body.refreshToken); 
      alert('로그인 성공'); 
    } 
    else { 
      const t = await res.text(); 
      alert('로그인 실패:'+t); 
    }
  };

  const me = async() => {

    const access = localStorage.getItem('access');

    if(!access) return alert('로그인 필요');

    const r = await fetch(
      '/api/user/me',
      {
        headers: {'Authorization':'Bearer '+ access}
      }
    );
    
    console.log("=== 내정보 클릭 ===")

    if(r.ok) { 
      
      const user = await r.json(); 
      const nickname = user.nickname;

      alert( !!nickname ? `🎉 환영합니다 ${nickname}님` : `🎉 환영합니다` ); 
    } 
    else {
      alert('me 실패');
    }
  };

  return (
    <div>
      <h3>로그인</h3>
      <input 
        placeholder="email" 
        value={email} 
        onChange={ e => setEmail(e.target.value) } 
      />
      <br/>
      <input 
        type="password" 
        placeholder="password" 
        value={pw} 
        onChange={ e => setPw(e.target.value)} 
      />
      <br/>

      <button onClick={login}>로그인</button>
      <button onClick={me}>내정보</button>
    </div>
  );
}