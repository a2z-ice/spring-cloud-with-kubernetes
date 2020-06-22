# https://istio.io/latest/docs/ops/best-practices/security/#configure-third-party-service-account-tokens
# istio command

#create istio-system namespace
kubectl apply -f istio-system-ns.yaml

#create istio-raw file
istioctl profile dump default > raw_default_profile.yaml

#Enable kiali, tracing,tracing,grafana by enabled: true

#Generate kubernetes file
istioctl manifest generate -f raw_default_profile.yaml --set values.global.jwtPolicy=first-party-jwt > istion-configuration-demo.yaml

#Apply Generated kubernetes manifest file
kubectl apply -f istion-configuration-demo.yaml

#Auto injection enabled
kubectl label namespace test-ns istio-injection=enabled

#6 Kiali secret
kubectl apply -f kiali-secret.yaml

while true; \
do \
curl http://192.168.0.140:30082/customerdetails?fastpassid=101;  \
sleep 2; \
done