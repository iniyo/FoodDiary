@echo off

REM UTF-8 코드 페이지로 변경
chcp 65001 >nul

REM 메뉴 출력
echo 1. tree /F src - 모든 파일 출력
echo 2. tree /A src - 깔끔한 ASCII 출력
echo 3. 디렉토리만 출력
set /p choice=Select an option (1-3):

REM 사용자 입력 처리
if %choice%==1 (
    tree /F src
) else if %choice%==2 (
    tree /A src
) else if %choice%==3 (
    REM 디렉토리만 출력
    for /d %%d in (src\*) do (
        echo %%d
    )
) else (
    echo Invalid choice.
)

pause
