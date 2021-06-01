# Medy - Cloud Architecture 

To Deploy an app in gcp:
gcloud app deploy

List all buckets and files:
gsutil ls
gsutil ls -lh gs://<bucket-name>

Download file:
gsutil cp gs://<bucket-name>/<dir-path>/package-1.0.tgz

Upload file:
gsutil cp <file-name> gs://<bucket-name>/<directory>/
  
Delete file:
gsutil rm gs://<bucket-name>/<filepath>
