```bash
# Example refernece: https://stackoverflow.com/questions/37458287/how-to-run-a-cron-job-inside-a-docker-container
# Quick note about a gotcha:
# If you're adding a script file and telling cron to run it, remember to
# RUN chmod 0744 /the_script
# Cron fails silently if you forget.

# docker build -t assaduzzaman/cron .
# docker push assaduzzaman/cron

# kubectl logs two-containers -c debian-container -f
```
