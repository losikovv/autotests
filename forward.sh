#!/bin/bash

# SHP Forward
shp_forward () {
  echo "Please enter stage number or name: "
  read -r stage
  kubectl -n s-sh-shp$stage port-forward pod/$(kubectl get pods -n s-sh-shp$stage | awk '{print $1}' | grep shopper-backend | head -1) 6432:6432
}

# STF Forward
stf_forward () {
  local PS3='Select STF option: '
  local options=("Forward" "Rake" "Back")
  local opt
  select opt in "${options[@]}"
  do
      case $opt in
      "Forward")
          while [ -z $stage ]; do
              echo "Please enter stage number or name: "
              read -r stage task
          done
          kubectl -n s-sb-stf$stage port-forward pod/$(kubectl get pods -n s-sb-stf$stage | awk '{print $1}' | grep app-stf-sbermarket | head -1) 3306:3306
          ;;
      "Rake")
          while [ -z $stage ] || [ -z $task ]; do
              echo "Please enter stage and task: "
              read -r stage task
          done
          kubectl exec -n s-sb-stf$stage -i -t -c puma $(kubectl get pods -n s-sb-stf$stage | awk '{print $1}' | grep app-stf-sbermarket | head -1) -- /vault/vault-env rake $task
          ;;
      "Back")
          return
          ;;
      *) echo "invalid option $REPLY";;
      esac
  done
}

# Main menu
PS3='Please enter option: '
options=("STF Forward" "SHP Forward" "Exit")
select opt in "${options[@]}"
do
    case $opt in
        "STF Forward")
            stf_forward
            ;;
        "SHP Forward")
            shp_forward
            ;;
        "Exit")
            exit
            ;;
        *) echo "invalid option $REPLY";;
    esac
done