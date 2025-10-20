import { useState } from "react"
import CustomAlert from "./CustomAlert"
import "../styles/Auth.css"

export default function Login() {
  const [email, setEmail] = useState("")
  const [pw, setPw] = useState("")
  const [alert, setAlert] = useState({ isOpen: false, message: "", type: "info" })

  const showAlert = (message, type = "info") => {
    setAlert({ isOpen: true, message, type })
  }

  const login = async () => {

    const res = await fetch("/api/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email,
        password: pw,
      }),
    });

    if (res.ok) {

      const body = await res.json();

      localStorage.setItem("access", body.accessToken);
      localStorage.setItem("refresh", body.refreshToken);

      showAlert("로그인 성공", "success");

    } else {

      const t = await res.text();

      showAlert("로그인 실패: " + t, "error");

    }
  }

  const me = async () => {

    const access = localStorage.getItem("access")

    if (!access) return showAlert("로그인 필요", "warning")

    const r = await fetch("/api/user/me", {
      headers: { Authorization: "Bearer " + access },
    })

    console.log("=== 내정보 클릭 ===")

    if (r.ok) {
      const user = await r.json()
      const nickname = user.nickname

      showAlert(!!nickname ? `🎉 환영합니다 ${nickname}님` : `🎉 환영합니다`, "success")
    } else {
      // const data = await r.json();
      // showAlert(data.error, 'error');
      showAlert("me 실패", 'error');
    }
  }

  return (
    <>
      <div className="auth-card">
        <h3 className="auth-title">로그인</h3>
        <form
          className="auth-form"
          onSubmit={(e) => {
            e.preventDefault()
            login()
          }}
        >
          <div className="input-group">
            <label className="input-label">이메일</label>
            <input
              className="auth-input"
              type="email"
              placeholder="example@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="input-group">
            <label className="input-label">비밀번호</label>
            <input
              className="auth-input"
              type="password"
              placeholder="비밀번호를 입력하세요"
              value={pw}
              onChange={(e) => setPw(e.target.value)}
            />
          </div>

          <div className="button-group">
            <button type="submit" className="primary-button">
              로그인
            </button>
            <button type="button" className="primary-button" onClick={me}>
              내정보
            </button>
          </div>
        </form>

        <div className="auth-link">
          <a href="/signup">회원가입하러 가기 →</a>
        </div>
      </div>

      <CustomAlert
        isOpen={alert.isOpen}
        message={alert.message}
        type={alert.type}
        onClose={() => setAlert({ ...alert, isOpen: false })}
      />
    </>
  )
}
