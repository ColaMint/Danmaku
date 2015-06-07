#include <windows.h>
#include "DanmakuComponent_DanmakuBoard.h"

VOID SetTransparent(HWND hDlg)
{
	LONG lStyle;
	lStyle = GetWindowLong(hDlg, GWL_EXSTYLE);

	SetWindowLong(hDlg, GWL_EXSTYLE, lStyle | WS_EX_TRANSPARENT | WS_EX_LAYERED);
	SetLayeredWindowAttributes(hDlg, RGB(1, 1, 1), 0, LWA_COLORKEY); 
	SetWindowPos(hDlg, HWND_TOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
}

JNIEXPORT void JNICALL Java_DanmakuComponent_DanmakuBoard_setWindowsTransparent
(JNIEnv *env, jobject jo, jstring js){
	HWND hd = FindWindowA(NULL, env->GetStringUTFChars(js , NULL));
	if (hd){
		SetTransparent(hd);
		CloseHandle(hd);
	}
}