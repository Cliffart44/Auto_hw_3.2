### [Решение](https://github.com/Cliffart44/Auto_hw_3.2/tree/API_testing) => [домашнего задания](https://github.com/netology-code/aqa-homeworks/tree/aqa4/sql#%D0%B7%D0%B0%D0%B4%D0%B0%D1%87%D0%B0-2---backend-vs-frontend-%D0%BD%D0%B5%D0%BE%D0%B1%D1%8F%D0%B7%D0%B0%D1%82%D0%B5%D0%BB%D1%8C%D0%BD%D0%B0%D1%8F). Задача №2 - Backend `vs` Frontend (необязательная)

---
#### Начало работы
Для работы с проектом есть возможность [клонировать](https://docs.github.com/en/free-pro-team@latest/github/creating-cloning-and-archiving-repositories/cloning-a-repository) репозиторий или [создать собственную копию](https://git-scm.com/book/ru/v2/GitHub-%D0%92%D0%BD%D0%B5%D1%81%D0%B5%D0%BD%D0%B8%D0%B5-%D1%81%D0%BE%D0%B1%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D0%BE%D0%B3%D0%BE-%D0%B2%D0%BA%D0%BB%D0%B0%D0%B4%D0%B0-%D0%B2-%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D1%8B) прямо на [GitHub](https://github.com/).

#### Prerequisites
* [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/)
* [AdoptOpenJDK jdk-11.0.9.1+1](https://adoptopenjdk.net/releases.html)
* [Docker](https://www.docker.com/get-started)

#### Установка и запуск `SUT`
1. Вначале следует запустить [`Docker`-контейнер](https://github.com/Cliffart44/Auto_hw_3.2/tree/2ed81de0c34a6e019a8b4534690d69cdb7437a22/Docker) с базой данных через [`docker-compose`](https://docs.docker.com/compose/#automated-testing-environments).
2. Затем можно [использовать](https://github.com/Cliffart44/Auto_hw_3.2/blob/2ed81de0c34a6e019a8b4534690d69cdb7437a22/java%20-jar.txt#L3) специальную сборку [`app-deadline.jar`](https://github.com/Cliffart44/Auto_hw_3.2/raw/2ed81de0c34a6e019a8b4534690d69cdb7437a22/artifacts/app-deadline.jar).
3. Для перезапуска `SUT` следует остановить `jar`-приложение с помощью [`Ctrl+C`](https://en.wikipedia.org/wiki/Control-C), контейнер с помощью `docker-compose down -v`.

---
---
[![Build status](https://ci.appveyor.com/api/projects/status/ce47gxrnula0c2b0/branch/API_testing?svg=true)](https://ci.appveyor.com/project/Cliffart44/auto-hw-3-2/branch/API_testing)

---
[![Gitter](https://badges.gitter.im/Cliffart44/community.svg)](https://gitter.im/Cliffart44/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
