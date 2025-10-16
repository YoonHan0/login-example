import React, {useState} from 'react';

export default function SignupForm() {

  const [email,setEmail] = useState('');
  const [pw,setPw] = useState('');

  const register = async() => {
    
    const res = await fetch(
      '/api/auth/register',
      {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({
          email,
          password: pw
        })
      }
    );

    if(res.ok) {
      alert('회원가입 성공');
    }
    else { 
      const t = await res.text(); alert('실패:'+t);
    }

  };

  const exists = async() => {
    const res = await fetch('/api/auth/exists?email='+encodeURIComponent(email));
    const ok = await res.json();
    alert(ok ? '이미 존재' : '사용 가능');
  };
  
  return (<div>
    <h3>Signup</h3>
    <input placeholder="email" value={email} onChange={e=>setEmail(e.target.value)} />
    <button onClick={exists}>중복체크</button><br/>
    <input type="password" placeholder="password" value={pw} onChange={e=>setPw(e.target.value)} />
    <button onClick={register}>회원가입</button>
  </div>);
}