#!/bin/bash

# Redis Forward
redis () {
  while true; do
    local PS3='Select Redis option: '
    local options=("Forward" "Back")
    local opt
    select opt in "${options[@]}"
    do
        case $opt in
        "Forward")
            while [ -z "$pod" ]; do
                echo "Please enter pod name: "
                read -r pod
            done
            # shellcheck disable=SC2046
            kubectl -n "$pod" port-forward svc/redis 6379:6379
            break
            ;;
        "Back") return;;
        *) echo "invalid option $REPLY";;
        esac
    done
  done
}

# SHP Forward
shp () {
  while true; do
    local PS3='Select SHP option: '
    local options=("Forward" "Back")
    local opt
    select opt in "${options[@]}"
    do
        case $opt in
        "Forward")
            while [ -z "$stage" ]; do
                echo "Please enter stage number or name: "
                read -r stage
            done
            # shellcheck disable=SC2046
            kubectl -n s-sh-shp"$stage" port-forward pod/$(kubectl get pods -n s-sh-shp"$stage" | awk '{print $1}' | grep shopper-backend | head -1) 6432:6432;
            break
            ;;
        "Back") return;;
        *) echo "invalid option $REPLY";;
        esac
    done
  done
}

# STF Forward
stf () {
  while true; do
    local PS3='Select STF option: '
    local options=("Forward" "Rake" "Back")
    local opt
    select opt in "${options[@]}"
    do
        case $opt in
        "Forward")
            while [ -z "$stage" ]; do
                echo "Please enter stage number or name: "
                read -r stage
            done
            # shellcheck disable=SC2046
            kubectl -n s-sb-stf"$stage" port-forward pod/$(kubectl get pods -n s-sb-stf"$stage" | awk '{print $1}' | grep app-stf-sbermarket | head -1) 3306:3306;
            break
            ;;
        "Rake")
            while [ -z "$stage" ] || [ -z "$task" ]; do
                echo "Please enter stage and task: "
                read -r stage task
            done
            # shellcheck disable=SC2046
            kubectl exec -n s-sb-stf"$stage" -i -t -c puma $(kubectl get pods -n s-sb-stf"$stage" | awk '{print $1}' | grep app-stf-sbermarket | head -1) -- /vault/vault-env rake "$task";
            break
            ;;
        "Back") return;;
        *) echo "invalid option $REPLY";;
        esac
    done
  done
}

# Main menu
while true; do
  PS3='Please select option: '
  options=("STF" "SHP" "REDIS" "Exit")
  select opt in "${options[@]}"
  do
      case $opt in
          "STF") stf; break ;;
          "SHP") shp; break ;;
          "REDIS") redis; break ;;
          "Exit") exit;;
          *) echo "invalid option $REPLY";;
      esac
  done
done