#!/bin/sh
set -e

env() {
  OS_NAME=$(uname -s)
  ENV="export ATST_APP_SECRET_KEY=$key"

  case $OS_NAME in
    'Darwin')
        BASH_PROFILE_PATH="${HOME}/.bash_profile"
        PROFILE_PATH="${HOME}/.profile"
        ZSH_PROFILE_PATH="${HOME}/.zshrc"

        touch "${PROFILE_PATH}"
        touch "${BASH_PROFILE_PATH}"
        touch "${ZSH_PROFILE_PATH}"

        echo "${ENV}" >>"${PROFILE_PATH}"
        echo "${ENV}" >>"${BASH_PROFILE_PATH}"
        echo "${ENV}" >>"${ZSH_PROFILE_PATH}"
      ;;
    'WindowsNT')
        setx ATST_APP_SECRET_KEY "$key"
      ;;
    *) ;;
  esac

  echo "done."
  # shellcheck disable=SC2104
  break
}

# Main request
while true; do
  while [ -z "$key" ]; do
      echo "RTFM https://wiki.sbmt.io/pages/viewpage.action?pageId=2210497018"
      echo "Please enter secret key: "
      read -r key
  done
  env break
done