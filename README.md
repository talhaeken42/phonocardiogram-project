# Phonocardiogram System Project

## English Description

This project was carried out through the collaboration of the Biomedical, Computer, and Electrical-Electronics Engineering departments at Ankara University Faculty of Engineering. The goal was to design and test a **Phonocardiogram (PCG)** system that records and analyzes heart sounds.

### Project Summary

The phonocardiogram system uses a chest-placed microphone to collect heart sounds, converts them into electrical signals, and visualizes them through software. The system includes both hardware (stethoscope and signal conditioning circuit) and software (Java-based GUI).

### Team

The project involved 12 students from three different engineering disciplines:

- **Biomedical Engineering**
- **Computer Engineering**
- **Electrical and Electronics Engineering**

### Contents

- `PhonocardiogramProjectReport.pdf` – Full technical project report  
- `src/` – Java source code for signal visualization interface  
  - `Main.java`  
  - `OfflineSignalPanel.java`  
  - `RealTimeSignalPanel.java`  
  - `SignalVisualizer.java`

### Usage

To run the Java Swing-based interface:

```bash
javac src/*.java
java src/Main
```

#### Requirements:
- Java JDK 8 or higher
- `javax.sound.sampled` library (included in Java SE)

### License

This project is licensed under the [MIT License](LICENSE). See the `LICENSE` file for more details.

## References

- All scientific references are included in the `PhonocardiogramProjectReport.pdf` document.

---

## Türkçe Açıklama

Bu proje, Ankara Üniversitesi Mühendislik Fakültesi Biyomedikal, Bilgisayar ve Elektrik-Elektronik Mühendisliği bölümlerinin iş birliğiyle gerçekleştirilmiştir. Projenin amacı, kalp seslerini kaydeden ve analiz eden bir **Phonocardiogram (PCG)** sistemi tasarlamak ve test etmektir.

### Proje Özeti

Phonocardiogram sistemi; göğüs üzerine yerleştirilen bir mikrofon aracılığıyla kalp seslerini toplayan, bu sinyalleri işleyerek görsel hale getiren bir araçtır. Sistem donanım (stetoskop ve sinyal işleme devresi) ve yazılım (Java GUI ile görselleştirme) bileşenlerinden oluşur.

### Ekip

Projede üç farklı mühendislik dalından toplam 12 öğrenci yer almıştır:

- **Biyomedikal Mühendisliği**
- **Bilgisayar Mühendisliği**
- **Elektrik-Elektronik Mühendisliği**

### İçerik

- `PhonocardiogramProjectReport.pdf` – Tam teknik proje raporu  
- `src/` – Java ile yazılmış sinyal görselleştirme arayüzü kaynak kodları  
  - `Main.java`  
  - `OfflineSignalPanel.java`  
  - `RealTimeSignalPanel.java`  
  - `SignalVisualizer.java`

### Kullanım

Java Swing tabanlı arayüzü çalıştırmak için:

```bash
javac src/*.java
java src/Main
```

#### Gereksinimler:
- Java JDK 8 veya üzeri
- `javax.sound.sampled` kütüphanesi (Java SE ile birlikte gelir)

### Lisans

Bu proje [MIT Lisansı](LICENSE) ile lisanslanmıştır. Detaylı bilgi için `LICENSE` dosyasına göz atabilirsiniz.
