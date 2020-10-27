kubectl create sa -n kubernetes-dashboard k8s-dashboard-viewer

cat <<EOF| kubectl create -f -
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: dashboard-viewonly
rules:
- apiGroups:
  - ""
  resources: ["*"]
  verbs:
  - get
  - list
  - watch
- apiGroups:
  - "extensions"
  resources: ["*"]
  verbs:
  - get
  - list
  - watch
- apiGroups:
  - "apps"
  resources: ["*"]
  verbs:
  - get
  - list
  - watch
  EOF
  ######################
  cat <<EOF|kubectl create -f -
  ---
apiVersion: rbac.authorization.k8s.io/v1beta1
kind: ClusterRoleBinding
metadata:
  name: k8s-dashboard-viewer
  labels:
    k8s-app: k8s-dashboard-viewer
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: dashboard-viewonly
subjects:
- kind: ServiceAccount
  name: k8s-dashboard-viewer
  namespace: kubernetes-dashboard
