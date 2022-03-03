#!/bin/bash

while [ -z "$basicauth" ]; do
    echo "Please enter stage Basic auth {user:pass}"
    read -r basicauth
done
while [ -z "$email" ]; do
    echo "Please enter stage admin email"
    read -r email
done
while [ -z "$pass" ]; do
    echo "Please enter stage admin password"
    read -r pass
done

# Stage array
declare -a StringArray=("stf-0" "stf-1" "stf-10" "stf-11" "stf-12" "stf-13" "stf-14" "stf-15" "stf-16" "stf-17" "stf-18" "stf-19" "stf-2" "stf-20" "stf-21" "stf-3" "stf-4" "stf-5" "stf-6" "stf-7" "stf-8" "stf-9" "stf-asml-0" "stf-asml-1" "stf-asml-2" "stf-bragi" "stf-deliveryclub" "stf-devops-domains" "stf-heimdallr-0" "stf-highgarden" "stf-integrations" "stf-jotunheimr" "stf-kraken" "stf-mannschaft-0" "stf-mannschaft-1" "stf-mannschaft-2" "stf-metro" "stf-mobileapp" "stf-naglfar-0" "stf-naglfar-1" "stf-naglfar-2" "stf-payments" "stf-preprod" "stf-retail" "stf-retail-2" "stf-retail-4" "stf-sberprime  " "stf-sbersuperapp" "stf-shopperapp-dev1" "stf-shopperapp-dev2" "stf-shopperapp-test" "stf-surstromming" "stf-ulfhednar-0" "stf-ulfhednar-1" "stf-ulfhednar-2" "stf-vargr-0")

# Iterate the string array using for loop
for val in ${StringArray[@]}; do
  echo "-------"
   echo $val
   # Auth
   curl --silent -c cockies$val.txt -X POST "https://$basicauth@$val.k-stage.sbermarket.tech:443/api/user_sessions" -H 'Accept: application/json, application/javascript, text/javascript, text/json' -H 'Content-Type: application/json; charset=UTF-8' -d "{\"user\":{\"password\": \"$pass\", \"remember_me\": true, \"email\": \"$email\" }}" > /dev/null
   # get feature flags
   curl --silent -b cockies$val.txt -X GET "https://$basicauth@$val.k-stage.sbermarket.tech:443/api/admin/feature_settings" | sed -e 's/[{}]/''/g' | awk -v RS=',"' -F:  '/^qa_mode_phone_auth_enabled/ {print $2}'
done


