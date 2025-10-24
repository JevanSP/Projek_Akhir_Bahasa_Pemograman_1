import java.util.Scanner;

public class projek_akhir_bp1 {

    // Data menu dan harga 
    static String[] kategori = { "Makanan", "Minuman" };
    static String[][] menu = {
            { "Tahu Gejrot", "Tahu Petis","Cireng" },
            { "Es Teh", "Es Jeruk" }
    };
    static int[][] harga = {
            { 15000, 8000, 10000 },
            { 4000, 5000 }
    };

    // Variabel untuk menyimpan total pemasukan
    static double totalPemasukan = 0;
    
    static int totalPelangganGlobal = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n++++++++++++++++++++++++++++++++++++");
            System.out.println("   SISTEM KIOS CAMILAN MAMISO");
            System.out.println("++++++++++++++++++++++++++++++++++++");
            System.out.println("1. Kasir");
            System.out.println("2. Admin");
            System.out.println("3. Owner");
            System.out.println("4. Exit");
            System.out.print("Pilih menu (1-4): ");
            pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    menuKasir(input);
                    break;
                case 2:
                    menuAdmin(input);
                    break;
                case 3:
                    menuOwner(input);
                    break;
                case 4:
                    System.out.println("Terima kasih! Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 4);

        input.close();
    }

    /**
     * Fungsi untuk menangani login dengan 3 kali percobaan.
     */
    static boolean login(Scanner input, String role, String correctUser, String correctPass) {
        int coba = 3;
        while (coba > 0) {
            System.out.println("\n=== LOGIN " + role.toUpperCase() + " ===");
            System.out.print("Silahkan masukkan username: ");
            String username = input.next(); // Menggunakan next() agar tidak ada masalah newline
            System.out.print("Silahkan masukkan password: ");
            String password = input.next();

            if (username.equals(correctUser) && password.equals(correctPass)) {
                System.out.println("✓ Login berhasil!");
                return true; // Login sukses
            }

            coba--;
            if (coba > 0) {
                System.out.println("Username atau password salah. Sisa percobaan: " + coba);
            } else {
                System.out.println("Akun diblokir. Terlalu banyak percobaan gagal.");
                return false; // Login gagal 3x
            }
        }
        return false;
    }

    /**
     * Menu Kasir
     */
    static void menuKasir(Scanner input) {
        // Kasir harus login dulu
        if (!login(input, "Kasir", "kasir", "kasir123")) {
            return; // Jika login gagal 3x, langsung kembali ke menu utama
        }

        System.out.println("\n=== MODE KASIR AKTIF ===");
        boolean adaPelangganLain = true;
        int PelangganSesiIni = 0; // Hitungan pelanggan untuk sesi kasir ini

        // Loop untuk melayani pelanggan
        while (adaPelangganLain) {
            PelangganSesiIni++; 
            System.out.println("\n--- Transaksi untuk Pelanggan ke-" + PelangganSesiIni + " (Sesi Ini) ---");
            
            // --- Mulai Transaksi Baru ---
            double totalTransaksi = 0;
            int nomorItem = 1;
            boolean selesaiTransaksi = false;

            // Loop ini HANYA untuk menambah item
            while (!selesaiTransaksi) { 
                System.out.println("\n--- Item ke-" + nomorItem + " (Pelanggan ke-" + PelangganSesiIni + ") ---");

                // Tampilan menu makanan yang diambil pada array diatas
                System.out.println("\nPilih Kategori:");
                for (int i = 0; i < kategori.length; i++) {
                    System.out.println((i + 1) + ". " + kategori[i]);
                }
                int opsiSelesai = kategori.length + 1;
                System.out.println(opsiSelesai + ". Selesai Transaksi");

                System.out.print("Pilih kategori (1-" + opsiSelesai + "): ");
                int pilKategori = input.nextInt();

                if (pilKategori == opsiSelesai) {
                    selesaiTransaksi = true;
                    continue;
                }

                if (pilKategori < 1 || pilKategori > kategori.length) {
                    System.out.println("Pilihan tidak valid!");
                    continue;
                }

                int idxKat = pilKategori - 1;

                // Tampilan submenu yang diambil pada array diatas
                // dan harga
                System.out.println("\nPilihan " + kategori[idxKat] + ":");
                for (int i = 0; i < menu[idxKat].length; i++) {
                    System.out.println((i + 1) + ". " + menu[idxKat][i] + " - Rp " + harga[idxKat][i]);
                }

                System.out.print("Pilih menu (1-" + menu[idxKat].length + "): ");
                int pilMenu = input.nextInt();

                if (pilMenu < 1 || pilMenu > menu[idxKat].length) {
                    System.out.println("Pilihan tidak valid!");
                    continue;
                }

                int idxMenu = pilMenu - 1;

                System.out.print("Jumlah: ");
                int jumlah = input.nextInt();

                if (jumlah <= 0) {
                    System.out.println("Jumlah harus lebih dari 0!");
                    continue;
                }

                int totalItem = harga[idxKat][idxMenu] * jumlah;
                totalTransaksi += totalItem;

                System.out.println("\n✓ " + menu[idxKat][idxMenu]);
                System.out.println("  Harga: Rp " + harga[idxKat][idxMenu] + " x " + jumlah + " = Rp " + totalItem);
                System.out.println("  Sub-total: Rp " + totalTransaksi);

                nomorItem++;
            }

            // --- Selesai Transaksi (Pembayaran) ---
            if (totalTransaksi > 0) {
                System.out.println("\n====================================");
                System.out.println("         STRUK PEMBAYARAN");
                System.out.println("       (Pelanggan ke-" + PelangganSesiIni + ")");
                System.out.println("====================================");
                System.out.println("------------------------------------");
                System.out.println("Total Transaksi: Rp " + totalTransaksi);

                System.out.print("Apakah member? (y/t): ");
                String member = input.next();

                double diskon = 0;
                if (member.equalsIgnoreCase("y")) {
                    diskon = totalTransaksi * 0.05; // Diskon 5%
                    System.out.println("Diskon 5%: Rp " + diskon);
                }

                double grandTotal = totalTransaksi - diskon;
                System.out.println("Grand Total: Rp " + grandTotal);
                System.out.println("====================================");

                System.out.print("Bayar: Rp ");
                double bayar = input.nextDouble();

                if (bayar >= grandTotal) {
                    double kembali = bayar - grandTotal;
                    System.out.println("Kembali: Rp " + kembali);
                    System.out.println("✓ Transaksi berhasil!");
                    
                    // ==========================================================
                    // PERUBAHAN 2: Tambah pemasukan DAN pelanggan
                    // ==========================================================
                    totalPemasukan += grandTotal; 
                    totalPelangganGlobal++; // Pelanggan dihitung HANYA JIKA berhasil bayar

                } else {
                    double kurang = grandTotal - bayar;
                    System.out.println("Uang kurang: Rp " + kurang + ". Transaksi dibatalkan.");
                }
            } else {
                System.out.println("Tidak ada transaksi.");
            }

            // 3. Tanya apakah ada pelanggan lain
            System.out.print("\nApakah ada pelanggan lain? (y/t): ");
            String lagi = input.next();
            if (lagi.equalsIgnoreCase("t")) {
                adaPelangganLain = false;
            }
        } 

        System.out.println("Sesi kasir selesai. Kembali ke menu utama...");
    }
    /**
     * Menu Admin - Mengubah harga
     */
    static void menuAdmin(Scanner input) {
        // Admin harus login dulu
        if (!login(input, "Admin", "admin", "admin123")) {
            return; // Gagal login, kembali ke menu utama
        }

        System.out.println("\n=== MODE ADMIN AKTIF ===");
        boolean selesai = false;

        while (!selesai) {
            System.out.println("\nPilih kategori untuk ubah harga:");
            for (int i = 0; i < kategori.length; i++) {
                System.out.println((i + 1) + ". " + kategori[i]);
            }
            int opsiKembali = kategori.length + 1;
            System.out.println(opsiKembali + ". Kembali ke Menu Utama");

            System.out.print("Pilih (1-" + opsiKembali + "): ");
            int pilKategori = input.nextInt();

            if (pilKategori == opsiKembali) {
                selesai = true;
                continue;
            }

            if (pilKategori < 1 || pilKategori > kategori.length) {
                System.out.println("Pilihan tidak valid!");
                continue;
            }

            int idxKat = pilKategori - 1;

            System.out.println("\nMenu " + kategori[idxKat] + ":");
            for (int i = 0; i < menu[idxKat].length; i++) {
                System.out.println((i + 1) + ". " + menu[idxKat][i] + " - Rp " + harga[idxKat][i]);
            }

            System.out.print("Pilih menu yang akan diubah harga (1-" + menu[idxKat].length + "): ");
            int pilMenu = input.nextInt();

            if (pilMenu < 1 || pilMenu > menu[idxKat].length) {
                System.out.println("Pilihan tidak valid!");
                continue;
            }

            int idxMenu = pilMenu - 1;

            System.out.print("Harga baru untuk " + menu[idxKat][idxMenu] + ": Rp ");
            int hargaBaru = input.nextInt();

            if (hargaBaru <= 0) {
                System.out.println("Harga harus lebih dari 0!");
                continue;
            }

            harga[idxKat][idxMenu] = hargaBaru;
            System.out.println("✓ Harga berhasil diubah!");
        }
    }

    
    //Menu Owner - Lihat pemasukan
    static void menuOwner(Scanner input) {
        // Owner harus login dulu
        if (!login(input, "Owner", "owner", "owner123")) {
            return; // Gagal login, kembali ke menu utama
        }

        System.out.println("\n=== MODE OWNER AKTIF ===");
        System.out.println("Total Pemasukan: Rp " + totalPemasukan);
        System.out.println("Jumlah Pelanggan: " + totalPelangganGlobal + " orang");
        System.out.println("\nTekan enter untuk kembali...");
        input.nextLine(); // Clear buffer
        input.nextLine(); // Tunggu input user
    }
}