# Aihemäärittely
### Aihe
Kyseessä on käyttöliittymän avulla toimiva symbolinen laskin, joka hyödyntää
Wolfram Alphan [Full Results API](https://products.wolframalpha.com/api/explorer/)
-rajapintaa ja tarjoaa käyttäjälle tukemansa ratkaisut tyyliteltynä LaTeX-koodina.
### Käyttäjät
Kuka tahansa. Pitää olla Wolframin AppID.
### Käyttäjien toiminnot
- Laskun syöttäminen sille varattuun kenttään.
- Kentän tyhjentäminen nappia painamalla.
- Tuettujen ratkaisujen esittäminen nappia painamalla.
- LaTeX-muotoisen vastauksen kopioiminen nappia painamalla.
- AppID:n vaihtaminen omassa näkymässään.
# Luokkakaavio
![Luokkakaavio](luokkakaavio.png)
# Rakennekuvaus
Ohjelma on jaettu viiteen pakkaukseen. IO-pakkaus sisältää APPID:n sisältävän tiedoston käsittelemiseen vaaditun toiminnallisuuden. GUI-pakkaus sisältää käyttöliittymän. WA-pakkaus sisältää API:a hyödyntävän toiminnallisuuden, jolla tuetut ratkaisut saadaan talteen. Latexconverter-pakkaus sisältää toiminnallisuuden ratkaisujen kääntämiseen. Calculator-pakkaus hyödyntää WA- ja Latexconverter-pakkauksia ja tarjoaa GUI:lle käännetyt ratkaisut.
# Käyttötapauksia
![Käyttötapaus 1](sekvenssikaavio_1.png)
![Käyttötapaus 2](sekvenssikaavio_2.png)
