import { useEffect, useState } from "react"
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Signup from "./components/Signup"
import Login from "./components/Login"
import CustomAlert from "./components/CustomAlert"
import "./styles/App.css"

function App() {
  
  const [alert, setAlert] = useState({ isOpen: false, message: "", type: "info" })

  useEffect(() => {
    const params = new URLSearchParams(window.location.search)
    const access = params.get("accessToken")
    const refresh = params.get("refreshToken")
    if (access) localStorage.setItem("access", access)
    if (refresh) localStorage.setItem("refresh", refresh)
  }, [])

  const handleSocialLogin = (flag) => {
    if (flag === "google") {
      window.location.href = "http://localhost:8080/oauth2/authorization/google"
    } else {
      window.location.href = "/oauth2/authorization/kakao"
    }
  }

  return (
    <>
      <div className="app-container">
        <div className="app-content">
          <div className="app-header">
            <h1 className="app-title">Welcome Back</h1>
            <p className="app-subtitle">간편하게 로그인하고 시작하세요</p>
          </div>

          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Login />} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<Signup />} />
            </Routes>
          </BrowserRouter>

          <div className="social-login-section">
            <p className="social-login-title">소셜 계정으로 시작하기</p>
            <button className="social-button social-button-google" onClick={() => handleSocialLogin("google")}>
              <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                <path
                  d="M19.6 10.227c0-.709-.064-1.39-.182-2.045H10v3.868h5.382a4.6 4.6 0 01-1.996 3.018v2.51h3.232c1.891-1.742 2.982-4.305 2.982-7.35z"
                  fill="#4285F4"
                />
                <path
                  d="M10 20c2.7 0 4.964-.895 6.618-2.423l-3.232-2.509c-.895.6-2.04.955-3.386.955-2.605 0-4.81-1.76-5.595-4.123H1.064v2.59A9.996 9.996 0 0010 20z"
                  fill="#34A853"
                />
                <path
                  d="M4.405 11.9c-.2-.6-.314-1.24-.314-1.9 0-.66.114-1.3.314-1.9V5.51H1.064A9.996 9.996 0 000 10c0 1.614.386 3.14 1.064 4.49l3.34-2.59z"
                  fill="#FBBC05"
                />
                <path
                  d="M10 3.977c1.468 0 2.786.505 3.823 1.496l2.868-2.868C14.959.99 12.695 0 10 0 6.09 0 2.71 2.24 1.064 5.51l3.34 2.59C5.192 5.736 7.396 3.977 10 3.977z"
                  fill="#EA4335"
                />
              </svg>
              Google로 계속하기
            </button>
            <button className="social-button social-button-kakao" onClick={() => handleSocialLogin("kakao")}>
              <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                <path
                  d="M10 0C4.477 0 0 3.582 0 8c0 2.842 1.883 5.337 4.703 6.771-.197.725-.641 2.41-.734 2.791-.11.456.167.45.361.327.153-.097 2.419-1.616 3.34-2.232.44.061.894.093 1.355.093 5.523 0 10-3.582 10-8S15.523 0 10 0z"
                  fill="#000000"
                />
              </svg>
              KakaoTalk으로 계속하기
            </button>
          </div>
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

export default App
