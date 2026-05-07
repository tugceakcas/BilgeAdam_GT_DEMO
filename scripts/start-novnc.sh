#!/usr/bin/env bash
set -euo pipefail

DISPLAY_VALUE="${DISPLAY:-:99}"
DISPLAY_NUMBER="${DISPLAY_VALUE#:}"
DISPLAY_NUMBER="${DISPLAY_NUMBER%%.*}"
NOVNC_PORT="${NOVNC_PORT:-6080}"
VNC_PORT="${VNC_PORT:-5900}"
SCREEN_SIZE="${NOVNC_RESOLUTION:-1920x1080x24}"
NOVNC_WEB_ROOT="${NOVNC_WEB_ROOT:-/usr/share/novnc}"

export DISPLAY="$DISPLAY_VALUE"

if [[ ! -S "/tmp/.X11-unix/X${DISPLAY_NUMBER}" ]]; then
  echo "Starting Xvfb on DISPLAY=${DISPLAY_VALUE}"
  Xvfb "$DISPLAY_VALUE" -screen 0 "$SCREEN_SIZE" -ac +extension RANDR >/tmp/gt-demo-xvfb.log 2>&1 &
  sleep 2
else
  echo "Xvfb display socket already exists for DISPLAY=${DISPLAY_VALUE}"
fi

if ! pgrep -x fluxbox >/dev/null 2>&1; then
  echo "Starting fluxbox window manager"
  fluxbox >/tmp/gt-demo-fluxbox.log 2>&1 &
else
  echo "fluxbox is already running"
fi

if ! pgrep -f "x11vnc.*rfbport ${VNC_PORT}" >/dev/null 2>&1; then
  echo "Starting x11vnc on port ${VNC_PORT}"
  x11vnc -display "$DISPLAY_VALUE" -forever -shared -nopw -listen 0.0.0.0 -rfbport "$VNC_PORT" -bg -o /tmp/gt-demo-x11vnc.log
else
  echo "x11vnc is already running on port ${VNC_PORT}"
fi

if pgrep -f "websockify.*${NOVNC_PORT}.*${VNC_PORT}" >/dev/null 2>&1; then
  echo "noVNC is already running on port ${NOVNC_PORT}"
else
  echo "Starting noVNC on port ${NOVNC_PORT}"
  websockify --web="$NOVNC_WEB_ROOT" "$NOVNC_PORT" "localhost:${VNC_PORT}" >/tmp/gt-demo-novnc.log 2>&1 &
fi

echo
echo "noVNC is ready."
echo "Open the forwarded port ${NOVNC_PORT} in Codespaces, then run WebCucumberTest with the web-novnc Java test config."
echo "Terminal alternative:"
echo "  DISPLAY=${DISPLAY_VALUE} mvn -Dwebdriver.headless=false -Dtest=com.gt.demo.web.runners.WebCucumberTest test"
