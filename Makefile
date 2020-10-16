.DEFAULT_GOAL := build

lint:
	lein check-idiomatic
	lein check-lint

build:
	lein uberjar

deploy:
	lein deploy clojars
