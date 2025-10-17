"use client"

import { useState } from "react"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog"
import { Button } from "@/components/ui/button"

// 커스텀 Alert 컴포넌트
function CustomAlert({ isOpen, onClose, title, message, onConfirm }) {
  const handleConfirm = () => {
    if (onConfirm) {
      onConfirm()
    }
    onClose()
  }

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>{title || "알림"}</DialogTitle>
          {message && <DialogDescription className="text-base">{message}</DialogDescription>}
        </DialogHeader>
        <DialogFooter>
          <Button onClick={handleConfirm} className="w-full">
            확인
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}

// 사용 예시 컴포넌트
export default function App() {
  const [alertOpen, setAlertOpen] = useState(false)

  const showAlert = () => {
    setAlertOpen(true)
  }

  const handleConfirm = () => {
    console.log("확인 버튼이 클릭되었습니다!")
    // 여기에 원하는 동작을 추가하세요
    alert("확인 버튼 동작 실행!")
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-50">
      <div className="text-center space-y-4">
        <h1 className="text-2xl font-bold text-gray-800">커스텀 Alert 예시</h1>
        <Button onClick={showAlert} size="lg">
          Alert 창 열기
        </Button>
      </div>

      <CustomAlert
        isOpen={alertOpen}
        onClose={() => setAlertOpen(false)}
        title="성공!"
        message="작업이 성공적으로 완료되었습니다."
        onConfirm={handleConfirm}
      />
    </div>
  )
}
