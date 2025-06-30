# 

## Model
www.msaez.io/#/81686317/storming/144e6981dffcd7312fb47a268bfd5842

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- book
- user
- bookpublish
- author
- dashboard


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- book
```
 http :8088/books id="id"title="title"content="content"userId="user_id"publishedAt="published_at"state="state"view="view"subscribers="subscribers"
```
- user
```
 http :8088/users id="id"role="role"name="name"email="email"point="point"plan="plan"state="state"telecom="telecom"password="password"
 http :8088/subscribes id="id"bookId="book_id"userId="user_id"state="state"expiredAt="expired_at"
```
- bookpublish
```
 http :8088/genData bookId="book_id"coverUrl="cover_url"summary="summary"downloadUrl="download_url"point="point"category="category"
```
- author
```
 http :8088/authors userId="user_id"state="state"portfolio="portfolio"profile="profile"
```
- dashboard
```
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
