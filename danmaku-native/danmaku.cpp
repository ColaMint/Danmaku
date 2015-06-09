#include <windows.h>
#include "com_danmaku_util_NativeUtil.h"

VOID SetTransparent(HWND hDlg, int R, int G, int B)
{
	LONG lStyle;
	lStyle = GetWindowLong(hDlg, GWL_EXSTYLE);

	SetWindowLong(hDlg, GWL_EXSTYLE, lStyle | WS_EX_TRANSPARENT | WS_EX_LAYERED);
	SetLayeredWindowAttributes(hDlg, RGB(R, G, B), 0, LWA_COLORKEY); 
	SetWindowPos(hDlg, HWND_TOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
}

JNIEXPORT void JNICALL Java_com_danmaku_util_NativeUtil_SetWindowBackgroundTransparent
(JNIEnv *env, jclass jc, jint jR, jint jG, jint jB, jstring js){
	HWND hd = FindWindowA(NULL, env->GetStringUTFChars(js, NULL));
	int R = jR;
	int G = jG;
	int B = jB;
	if (hd){
		SetTransparent(hd,R,G,B);
		CloseHandle(hd);
	}
}
