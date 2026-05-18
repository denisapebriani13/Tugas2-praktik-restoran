import java.util.Scanner;

public class Main {
    static final int MAKS_MENU = 20;
    static Menu[] daftarMenu = new Menu[MAKS_MENU];
    static int jumlahMenu = 7;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inisialisasiMenu();
        tampilkanMenuUtama();
    }

    static void inisialisasiMenu() {
        daftarMenu[0] = new Menu("Nasi Goreng", 25000, "makanan");
        daftarMenu[1] = new Menu("Mie Ayam", 20000, "makanan");
        daftarMenu[2] = new Menu("Ayam Bakar", 35000, "makanan");
        daftarMenu[3] = new Menu("Es Teh", 8000, "minuman");
        daftarMenu[4] = new Menu("Es Jeruk", 12000, "minuman");
        daftarMenu[5] = new Menu("Kopi", 15000, "minuman");
        daftarMenu[6] = new Menu("Air Mineral", 5000, "minuman");
    }

    static void tampilkanDaftarMenu() {
        System.out.println("\n=== DAFTAR MENU ===");
        for (int i = 0; i < jumlahMenu; i++) {
            System.out.printf("%2d. %s\n", i + 1, daftarMenu[i]);
        }
    }

    static void tampilkanMenuUtama() {
        while (true) {
            System.out.println("\n=========================================");
            System.out.println("       SELAMAT DATANG DI RESTORAN");
            System.out.println("=========================================");
            System.out.println("1. Pesan Makanan/Minuman");
            System.out.println("2. Kelola Menu (Pemilik)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    prosesPesanan();
                    break;
                case 2:
                    menuKelolaMenu();
                    break;
                case 3:
                    System.out.println("Terima kasih! Program selesai.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void prosesPesanan() {
        final int MAKS_PESANAN = 20;
        Menu[] pesanan = new Menu[MAKS_PESANAN];
        int[] jumlahPesanan = new int[MAKS_PESANAN];
        int jumlahItem = 0;

        tampilkanDaftarMenu();

        while (true) {
            System.out.print("\nMasukkan nomor menu (atau ketik 'selesai'): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("selesai")) break;

            try {
                int nomor = Integer.parseInt(input);
                if (nomor < 1 || nomor > jumlahMenu) {
                    System.out.println("Nomor menu tidak valid!");
                    continue;
                }

                Menu item = daftarMenu[nomor - 1];
                System.out.print("Jumlah " + item.getNama() + ": ");
                int qty = scanner.nextInt();
                scanner.nextLine();

                if (qty > 0) {
                    pesanan[jumlahItem] = item;
                    jumlahPesanan[jumlahItem] = qty;
                    jumlahItem++;
                }
            } catch (Exception e) {
                System.out.println("Input tidak valid! Masukkan nomor atau 'selesai'.");
            }
        }

        if (jumlahItem == 0) {
            System.out.println("Tidak ada pesanan.");
            return;
        }

        cetakStruk(pesanan, jumlahPesanan, jumlahItem);
    }

    static void cetakStruk(Menu[] pesanan, int[] jumlahPesanan, int jumlahItem) {
        double subtotal = 0;
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    STRUK PESANAN");
        System.out.println("=".repeat(60));

        for (int i = 0; i < jumlahItem; i++) {
            double totalItem = pesanan[i].getHarga() * jumlahPesanan[i];
            subtotal += totalItem;
            System.out.printf("%-25s x%-3d = Rp %,8.0f\n", 
                    pesanan[i].getNama(), jumlahPesanan[i], totalItem);
        }

        double pajak = subtotal * 0.10;
        double biayaLayanan = 20000;
        double totalSebelumDiskon = subtotal + pajak + biayaLayanan;

        double diskon = 0;
        if (subtotal > 100000) {
            diskon = totalSebelumDiskon * 0.10;
        }

        boolean promoMinuman = subtotal > 50000;

        double totalAkhir = totalSebelumDiskon - diskon;

        System.out.println("-".repeat(60));
        System.out.printf("Subtotal          : Rp %,10.0f\n", subtotal);
        System.out.printf("Pajak (10%%)       : Rp %,10.0f\n", pajak);
        System.out.printf("Biaya Layanan     : Rp %,10.0f\n", biayaLayanan);
        if (diskon > 0) System.out.printf("Diskon 10%%        : -Rp %,10.0f\n", diskon);
        if (promoMinuman) System.out.println("Promo: Beli 1 Gratis 1 Minuman diterapkan!");
        System.out.printf("TOTAL BAYAR       : Rp %,10.0f\n", totalAkhir);
        System.out.println("=".repeat(60));
        System.out.println("Terima kasih telah berkunjung!");
    }

    static void menuKelolaMenu() {
        while (true) {
            System.out.println("\n=== KELOLA MENU ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Lihat Semua Menu");
            System.out.println("5. Kembali ke Menu Utama");
            System.out.print("Pilih: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: tambahMenu(); break;
                case 2: ubahMenu(); break;
                case 3: hapusMenu(); break;
                case 4: tampilkanDaftarMenu(); break;
                case 5: return;
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void tambahMenu() {
        if (jumlahMenu >= MAKS_MENU) {
            System.out.println("Menu sudah penuh!");
            return;
        }
        System.out.print("Nama Menu: ");
        String nama = scanner.nextLine();
        System.out.print("Harga: Rp ");
        double harga = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Kategori (makanan/minuman): ");
        String kategori = scanner.nextLine();

        daftarMenu[jumlahMenu] = new Menu(nama, harga, kategori);
        jumlahMenu++;
        System.out.println("✅ Menu berhasil ditambahkan!");
    }

    static void ubahMenu() {
        tampilkanDaftarMenu();
        System.out.print("Nomor menu yang ingin diubah: ");
        int nomor = scanner.nextInt();
        scanner.nextLine();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        Menu m = daftarMenu[nomor - 1];
        System.out.print("Nama baru: ");
        String nama = scanner.nextLine();
        if (!nama.isEmpty()) m.setNama(nama);

        System.out.print("Harga baru: ");
        String hargaStr = scanner.nextLine();
        if (!hargaStr.isEmpty()) m.setHarga(Double.parseDouble(hargaStr));

        System.out.print("Kategori baru: ");
        String kat = scanner.nextLine();
        if (!kat.isEmpty()) m.setKategori(kat);

        System.out.println("✅ Menu berhasil diubah!");
    }

    static void hapusMenu() {
        tampilkanDaftarMenu();
        System.out.print("Nomor menu yang ingin dihapus: ");
        int nomor = scanner.nextInt();
        scanner.nextLine();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin ingin menghapus " + daftarMenu[nomor-1].getNama() + "? (Y/N): ");
        String konfirm = scanner.nextLine();

        if (konfirm.equalsIgnoreCase("Y")) {
            for (int i = nomor - 1; i < jumlahMenu - 1; i++) {
                daftarMenu[i] = daftarMenu[i + 1];
            }
            daftarMenu[jumlahMenu - 1] = null;
            jumlahMenu--;
            System.out.println("✅ Menu berhasil dihapus!");
        }
    }
}