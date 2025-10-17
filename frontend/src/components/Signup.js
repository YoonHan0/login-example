"use client"

import { useState, useEffect } from "react"
import { useLocation } from "react-router-dom"
import CustomAlert from "./CustomAlert"
import "../styles/Auth.css"

export default function Signup() {
  const location = useLocation()
  const params = new URLSearchParams(location.search)

  const [email, setEmail] = useState("")
  const [pw, setPw] = useState("")
  const [name, setName] = useState("")
  const [dupChecked, setDupChecked] = useState(false)
  const [isEmailAvailable, setIsEmailAvailable] = useState(null)
  const [alert, setAlert] = useState({ isOpen: false, message: "", type: "info" })

  const showAlert = (message, type = "info") => {
    setAlert({ isOpen: true, message, type })
  }

  // URL 파라미터에서 이메일/이름 가져오기
  useEffect(() => {
    const emailParam = params.get("email")
    const nameParam = params.get("name")

    if (emailParam) {
      setEmail(emailParam)
      setDupChecked(false)
      setIsEmailAvailable(null)
    }
    if (nameParam) {
      setName(nameParam)
    }
  }, [location.search])

  const register = async () => {
    if (!dupChecked) {
      showAlert("이메일 중복체크를 먼저 진행해주세요.", "warning")
      return
    }

    if (isEmailAvailable === false) {
      showAlert("이미 존재하는 이메일입니다.", "error")
      return
    }

    // 비밀번호 유효성 체크
    const pwRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/

    if (!pwRegex.test(pw)) {
      showAlert("비밀번호는 8자 이상, 숫자, 대문자, 특수문자를 모두 포함해야 합니다.", "warning")
      return
    }

    const res = await fetch("/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email,
        password: pw,
        nickname: name,
      }),
    })

    if (res.ok) {
      showAlert("회원가입 성공", "success")
    } else {
      const t = await res.text()
      showAlert("실패: " + t, "error")
    }
  }

  const exists = async () => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

    if (!emailRegex.test(email)) {
      showAlert("이메일 형식이 올바르지 않습니다.", "warning")
      return
    }

    const res = await fetch("/api/auth/exists?email=" + encodeURIComponent(email))
    const ok = await res.json()
    showAlert(ok ? "이미 존재" : "사용 가능", ok ? "error" : "success")
    setDupChecked(true)
    setIsEmailAvailable(!ok)
  }

  return (
    <>
      <div className="auth-card">
        <h3 className="auth-title">회원가입</h3>
        <form
          className="auth-form"
          onSubmit={(e) => {
            e.preventDefault()
            register()
          }}
        >
          <div className="input-group">
            <label className="input-label">이메일</label>
            <div className="input-with-button">
              <input
                className="auth-input"
                type="email"
                placeholder="example@email.com"
                value={email}
                onChange={(e) => {
                  setEmail(e.target.value)
                  setDupChecked(false)
                  setIsEmailAvailable(null)
                }}
              />
              <button type="button" className="secondary-button" onClick={exists}>
                중복체크
              </button>
            </div>
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
            <p className="password-hint">8자 이상, 대문자, 숫자, 특수문자 포함</p>
          </div>

          <div className="input-group">
            <label className="input-label">이름</label>
            <input
              className="auth-input"
              type="text"
              placeholder="이름을 입력하세요"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>

          <button type="submit" className="primary-button">
            회원가입
          </button>
        </form>

        <div className="auth-link">
          <a href="/login">← 로그인으로 돌아가기</a>
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
