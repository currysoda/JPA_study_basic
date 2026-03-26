# PowerShell UTF-8 인코딩 설정 및 실행 스크립트
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8
chcp 65001 | Out-Null  # UTF-8 코드 페이지 설정

# JVM 옵션 설정
$env:JAVA_TOOL_OPTIONS = "-Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8"

Write-Host "UTF-8 인코딩 설정 완료. JpaMain 실행 중..." -ForegroundColor Green

# Java 클래스 직접 실행 (Gradle 빌드 후)
# 먼저 빌드
.\gradlew.bat build -x test

# 실행
$classpath = "build/classes/java/main;build/resources/main"
if (Test-Path "build/libs") {
    Get-ChildItem "build/libs/*.jar" | ForEach-Object { $classpath += ";$_" }
}

java -Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 -cp $classpath hellojpa.JpaMain
