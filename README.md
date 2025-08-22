## API

### Vastuste arv HTTP koodide kaupa etteantud kuus

**Endpoint** ```/stats/{year}/{month}/codes ```<br />
- [localhost:8080/stats/2023/08/codes ](http://localhost:8080/stats/2023/08/codes)
- [localhost:8080/stats/2023/09/codes](http://localhost:8080/stats/2023/09/codes)
- [localhost:8080/stats/2023/10/codes](http://localhost:8080/stats/2023/10/codes)

### Keskmine päringu aeg (ms) veebilehtedel etteantud kuus

**Endpoint** ```/stats/{year}/{month}/avg-duration ``` <br />
- [localhost:8080/stats/2023/08/avg-duration](http://localhost:8080/stats/2023/08/avg-duration)
- [localhost:8080/stats/2023/09/avg-duration](http://localhost:8080/stats/2023/09/avg-duration)
- [localhost:8080/stats/2023/10/avg-duration](http://localhost:8080/stats/2023/10/avg-duration)

### Antud veakoodi saanud päringute arv eri lehtedel etteantud kuus

**Endpoint** ```/stats/{year}/{month}/errors/{code}``` <br />
**Paar näidet** <br />
- [localhost:8080/stats/2023/08/errors/200](http://localhost:8080/stats/2023/08/errors/200)
- [localhost:8080/stats/2023/09/errors/500](http://localhost:8080/stats/2023/09/errors/500)
- [localhost:8080/stats/2023/10/errors/404](http://localhost:8080/stats/2023/10/errors/404)
