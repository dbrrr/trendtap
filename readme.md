<img src="logo.png" width="30%" alt="Polylith" id="logo">

The Polylith documentation can be found here:

- The [high-level documentation](https://polylith.gitbook.io/polylith)
- The [Polylith Tool documentation](https://polylith.gitbook.io/polylith/poly)
- The [RealWorld example app documentation](https://github.com/furkan3ayraktar/clojure-polylith-realworld-example-app)

You can also get in touch with the Polylith Team on [Slack](https://clojurians.slack.com/archives/C013B7MQHJQ).

<h1>trendtap</h1>

<p>Add your workspace documentation here...</p>


# Dev setup

docker run -d --name dev-postgres -e POSTGRES_PASSWORD=psql -v ${HOME}/postgres-data:/var/lib/postgresql/data -p 5432:5432 postgres

docker exec -it dev-postgres psql -U postgres
