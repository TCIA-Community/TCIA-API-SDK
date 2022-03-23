Python Client
================================================

* Requires Python 3.8 or above





### Steps to execute `sample.py`:

```

docker build -t tcia .
docker run -it -w /workdir -v $PWD:/workdir tcia bash
python src/sample.py

```