"use client"
import { createPortal } from "react-dom"
import "../styles/CustomAlert.css"

const CustomAlert = ({ isOpen, onClose, message, type = "info" }) => {
  if (!isOpen) return null

  const handleConfirm = () => {
    onClose()
  }

  const getIcon = () => {
    switch (type) {
      case "success":
        return "✓"
      case "error":
        return "✕"
      case "warning":
        return "!"
      default:
        return "✓"
    }
  }

  return createPortal(
    <div className="custom-alert-overlay" onClick={handleConfirm}>
      <div className="custom-alert-container" onClick={(e) => e.stopPropagation()}>
        <div className={`custom-alert-icon custom-alert-${type}`}>{getIcon()}</div>
        <div className="custom-alert-message">{message}</div>
        <button className="custom-alert-button" onClick={handleConfirm}>
          확인
        </button>
      </div>
    </div>,
    document.body,
  )
}

export default CustomAlert
