# .bash_profile

# Get the aliases and functions
if [ -f ~/.bashrc ]; then
	. ~/.bashrc
fi

# User specific environment and startup programs

ISTIO_HOME=/home/cent-master140/istio-1.6.3
PATH=$PATH:$HOME/.local/bin:$HOME/bin:$ISTIO_HOME/bin:

export PATH
