FROM debian
RUN apt-get update && apt-get -y install -qq --force-yes cron && apt-get install -y curl procps
# Copy hello-cron file to the cron.d directory
COPY hello-cron /etc/cron.d/hello-cron
 
# Give execution rights on the cron job
RUN chmod 0644 /etc/cron.d/hello-cron

# Apply cron job
RUN crontab /etc/cron.d/hello-cron
 
# Create the log file to be able to run tail
RUN touch /var/log/cron.log
 
# Run the command on container startup
CMD ["cron", "-f"]

# Example refernece: https://stackoverflow.com/questions/37458287/how-to-run-a-cron-job-inside-a-docker-container
# Quick note about a gotcha:
# If you're adding a script file and telling cron to run it, remember to
# RUN chmod 0744 /the_script
# Cron fails silently if you forget.

# docker build -t assaduzzaman/cron .
# docker push assaduzzaman/cron

# kubectl logs two-containers -c debian-container -f
