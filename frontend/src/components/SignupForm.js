import React, {useState} from 'react';

export default function SignupForm() {

  const [email, setEmail] = useState('');
  const [pw, setPw] = useState('');
  const [dupChecked, setDupChecked] = useState(false);
  const [isEmailAvailable, setIsEmailAvailable] = useState(null);

  const register = async() => {
    
    if (!dupChecked) {
      alert('이메일 중복체크를 먼저 진행해주세요.');
      return;
    }

    if (isEmailAvailable === false) {
      alert('이미 존재하는 이메일입니다.');
      return;
    }
    
    // 비밀번호 유효성 체크
    const pwRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}$/;

    if (!pwRegex.test(pw)) {
      alert('비밀번호는 8자 이상, 숫자, 대문자, 특수문자를 모두 포함해야 합니다.');
      return;
    }
    
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

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(email)) {
      alert('이메일 형식이 올바르지 않습니다.');
      return;
    }

    const res = await fetch('/api/auth/exists?email='+encodeURIComponent(email));
    const ok = await res.json();
    alert(ok ? '이미 존재' : '사용 가능');
    setDupChecked(true);
    setIsEmailAvailable(!ok);
  };
  
  return (
    <div>
      <h3>회원가입</h3>
      <input 
        placeholder="email" 
        value={email} 
        onChange={ e => { 
            setEmail(e.target.value); 
            setDupChecked(false); 
            setIsEmailAvailable(null); 
          }
        } 
      />
      <button onClick={exists}>중복체크</button><br/>
      <input 
        type="password" 
        placeholder="password" 
        value={pw} 
        onChange={ e => setPw(e.target.value) } 
      />
      <button onClick={register}>회원가입</button>
    </div>
  );
}