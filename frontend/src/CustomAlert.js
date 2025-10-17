"use client"
import { createPortal } from "react-dom"
import "./CustomAlert.css"

const CustomAlert = ({ isOpen, onClose, title, message, onConfirm }) => {
  if (!isOpen) return null

  const handleConfirm = () => {
    if (onConfirm) {
      onConfirm()
    }
    onClose()
  }

  const handleBackdropClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose()
    }
  }

  return createPortal(
    <div className="alert-overlay" onClick={handleBackdropClick}>
      <div className="alert-container">
        <div className="alert-header">
          <h3 className="alert-title">{title || "알림"}</h3>
        </div>
        <div className="alert-body">
          <p className="alert-message">{message}</p>
        </div>
        <div className="alert-footer">
          <button className="alert-button" onClick={handleConfirm}>
            확인
          </button>
        </div>
      </div>
    </div>,
    document.body,
  )
}

export default CustomAlert
