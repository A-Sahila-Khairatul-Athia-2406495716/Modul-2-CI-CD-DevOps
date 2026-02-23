https://eshop-advprog-sahilaathia.koyeb.app/

<details><summary><b>Module 01 - Coding Standards</b></summary>

# Reflection 1

**Clean Code Principles dan Secure Coding Practices yang Telah Diterapkan:**
- Separation of concerns: Kode dipisahkan ke dalam package yang berbeda (model, repository, service, controller) sesuai tanggung jawabnya masing-masing, sehingga lebih mudah dipahami dan di-maintain.
- Meaningful names: Nama yang digunakan deskriptif untuk class, method, dan variable. Contoh: ProductController, createProduct(), dan productList yang langsung menjelaskan fungsinya tanpa perlu comment tambahan.
- Focused functions: Setiap method yang dibuat fokus untuk melakukan satu tugas saja. Misalnya createProductPage() hanya menampilkan halaman form, sedangkan createProductPost() khusus untuk memproses data produk baru.
- Separation of logic: Business logic dipisahkan di service layer, tidak langsung di controller, sehingga lebih aman dan mudah untuk ditambahkan security check di masa depan.
- UUID generation: Menggunakan java.util.UUID untuk membuat ID produk secara acak dan unik, sehingga user tidak bisa menebak ID produk lain hanya dengan mencoba angka yang berurutan di URL.

**Improvement yang Bisa Dilakukan:**
- Error Handling: Saat ini error handling masih minimal. Perlu ditambahkan try-catch dan validasi untuk handle edge cases seperti product dengan ID yang tidak ada saat edit/delete.
- Input Sanitization: Melakukan pembersihan pada input teks untuk memastikan tidak ada tag HTML atau script berbahaya yang dimasukkan oleh user melalui form.


# Reflection 2

### (1)
- Menulis unit test membuat saya merasa lebih yakin dengan kode yang saya buat dan lebih tenang saat development. Unit test membantu saya memastikan setiap method sudah bekerja sesuai ekspektasi.
- Menurut saya, jumlah unit test yang perlu dibuat untuk suatu class tidak menentu, yang penting minimal setiap method public harus memiliki satu test untuk positive dan negative scenario. Untuk method yang kompleks, mungkin perlu lebih banyak test untuk cover berbagai case.
- Untuk memastikan unit test yang dibuat sudah cukup atau belum, bisa gunakan code coverage sebagai pengukur.
- Akan tetapi, 100% code coverage tidak menjamin kode bebas dari bug atau error, karena code coverage hanya mengukur baris kode yang dijalankan, bukan apakah logika tersebut sudah benar dalam menangani semua kemungkinan skenario.

### (2)
- Membuat functional test suite baru dengan setup procedures dan instance variables yang sama akan menurunkan code quality karena terjadi code duplication.
- Potensi masalah clean code:
  - Violation of DRY Principle: Setup WebDriver dan konfigurasi yang sama akan ditulis berulang di setiap test class
  - Redundant Code: Instance variables dan setup method akan ter-duplicate di semua functional test suite
  - Poor Maintainability: Jika ada perubahan konfigurasi, harus mengubah di banyak file
- Saran improvement adalah menerapkan prinsip DRY (Don't Repeat Yourself) dengan membuat sebuah base class yang menampung prosedur setup umum, sehingga test class lainnya cukup inherit dari base class tersebut.
</details>

<summary><b>Module 02 - CI/CD & DevOps</b></summary>

[![Deploy to Koyeb](https://www.koyeb.com/static/images/deploy/button.svg)](https://app.koyeb.com/deploy?name=eshop&type=git&repository=A-Sahila-Khairatul-Athia-2406495716%2FModul-2-CI-CD-DevOps&branch=main&builder=dockerfile&instance_type=free&regions=was&instances_min=0&autoscaling_sleep_idle_delay=3900&ports=8080%3Bhttp%3B%2F&hc_protocol%5B8080%5D=tcp&hc_grace_period%5B8080%5D=5&hc_interval%5B8080%5D=30&hc_restart_limit%5B8080%5D=3&hc_timeout%5B8080%5D=5&hc_path%5B8080%5D=%2F&hc_method%5B8080%5D=get)

# Reflection

### 1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.

Setelah melakukan analisis menggunakan tool PMD, saya menemukan dan memperbaiki beberapa code quality issues sebagai berikut:
- JUnit tests should include assert or fail: Isu ini terdapat pada `EshopApplicationTests.java`. Saya memperbaikinya dengan menambahkan `assertDoesNotThrow` untuk memanggil metode main aplikasi, sehingga dapat terverifikasi bahwa aplikasi dapat di-load tanpa menimbulkan exception.
- Avoid duplication of string literals: Banyak string yang digunakan berulang kali di dalam file test (seperti ID produk dan nama produk). Saya memperbaiki isu ini dengan mengextract string-string tersebut menjadi constant `private static final String` untuk mematuhi prinsip DRY (Don't Repeat Yourself) dan memudahkan pemeliharaan kode kedepannya.
- Unit tests should not contain more than 1 assert(s): Beberapa test methods memiliki lebih dari satu assertion. Saya menggunakan `assertAll()` untuk mengelompokkannya sehingga assertion count dapat dikurangi.
- JUnit assertions should include a message: PMD mendeteksi banyak assertion yang tidak memiliki pesan deskriptif. Sehingga saya menambahkan argumen message di setiap fungsi assertion agar kegagalan tes lebih mudah diidentifikasi penyebabnya.

### 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment?



