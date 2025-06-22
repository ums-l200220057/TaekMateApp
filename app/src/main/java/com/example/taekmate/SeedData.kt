package com.example.taekmate

import android.content.Context

class SeedData {
    fun seedItems(context: Context) {
        val dbHelper = DBHelper(context)

        // Cek apakah sudah ada data
        if (dbHelper.getAllItems().isNotEmpty()) return

        // SERAGAM
        dbHelper.insertItem("Do-Bok Kerah Putih", "Seragam", 100000, 25000, R.drawable.dobokkerahputih)
        dbHelper.insertItem("Do-Bok Kerah Poom", "Seragam", 120000, 30000, R.drawable.dobokkerahpoom)
        dbHelper.insertItem("Do-Bok Kerah Hitam", "Seragam", 120000, 30000, R.drawable.dobokkerahhitam)

        dbHelper.insertItem("Do-Bok Poomsae Merah", "Seragam", 120000, 30000, R.drawable.dobokpoommerah)
        dbHelper.insertItem("Do-Bok Poomsae Biru", "Seragam", 120000, 30000, R.drawable.dobokpoombiru)
        dbHelper.insertItem("Do-Bok Poomsae Abu", "Seragam", 120000, 30000, R.drawable.dobokpoomabu)

        dbHelper.insertItem("Sabuk Putih", "Seragam", 120000, 30000, R.drawable.putih)
        dbHelper.insertItem("Sabuk Kuning", "Seragam", 120000, 30000, R.drawable.kuning)
        dbHelper.insertItem("Sabuk Kuning Strip Hijau", "Seragam", 120000, 30000, R.drawable.kuningstrip)
        dbHelper.insertItem("Sabuk Hijau", "Seragam", 120000, 30000, R.drawable.hijau)
        dbHelper.insertItem("Sabuk Hijau Strip Biru", "Seragam", 120000, 30000, R.drawable.hijaustrip)
        dbHelper.insertItem("Sabuk Biru", "Seragam", 120000, 30000, R.drawable.biru)
        dbHelper.insertItem("Sabuk Biru Strip Merah", "Seragam", 120000, 30000, R.drawable.birustrip)
        dbHelper.insertItem("Sabuk Merah", "Seragam", 120000, 30000, R.drawable.merah)
        dbHelper.insertItem("Sabuk Merah Strip 1", "Seragam", 120000, 30000, R.drawable.merahstrip1)
        dbHelper.insertItem("Sabuk Merah Strip 2", "Seragam", 120000, 30000, R.drawable.merahstrip2)
        dbHelper.insertItem("Sabuk Hitam Dan I", "Seragam", 120000, 30000, R.drawable.dan1)
        dbHelper.insertItem("Sabuk Hitam Dan II", "Seragam", 120000, 30000, R.drawable.dan2)
        dbHelper.insertItem("Sabuk Hitam Dan III", "Seragam", 120000, 30000, R.drawable.dan3)
        dbHelper.insertItem("Sabuk Hitam Dan IV", "Seragam", 120000, 30000, R.drawable.dan4)
        dbHelper.insertItem("Sabuk Hitam Dan V", "Seragam", 120000, 30000, R.drawable.dan5)
        dbHelper.insertItem("Sabuk Hitam Dan VI", "Seragam", 120000, 30000, R.drawable.dan6)
        dbHelper.insertItem("Sabuk Hitam Dan VII", "Seragam", 120000, 30000, R.drawable.dan7)
        dbHelper.insertItem("Sabuk Hitam Dan VIII", "Seragam", 120000, 30000, R.drawable.dan8)

        // PROTECTOR
        dbHelper.insertItem("Head Guard S", "Protector", 80000, 20000, R.drawable.headguard)
        dbHelper.insertItem("Head Guard M", "Protector", 80000, 20000, R.drawable.headguard)
        dbHelper.insertItem("Head Guard L", "Protector", 80000, 20000, R.drawable.headguard)

        dbHelper.insertItem("Body Protector Size 1", "Protector", 110000, 25000, R.drawable.hugo)
        dbHelper.insertItem("Body Protector Size 2", "Protector", 110000, 25000, R.drawable.hugo)
        dbHelper.insertItem("Body Protector Size 3", "Protector", 110000, 25000, R.drawable.hugo)
        dbHelper.insertItem("Body Protector Size 4", "Protector", 110000, 25000, R.drawable.hugo)
        dbHelper.insertItem("Body Protector Size 5", "Protector", 110000, 25000, R.drawable.hugo)

        dbHelper.insertItem("Arms Guard S", "Protector", 110000, 25000, R.drawable.armsprotector)
        dbHelper.insertItem("Arms Guard M", "Protector", 110000, 25000, R.drawable.armsprotector)
        dbHelper.insertItem("Arms Guard L", "Protector", 110000, 25000, R.drawable.armsprotector)

        dbHelper.insertItem("Hand Guard/Gloves S", "Protector", 120000, 30000, R.drawable.handsprotector)
        dbHelper.insertItem("Hand Guard/Gloves M", "Protector", 120000, 30000, R.drawable.handsprotector)
        dbHelper.insertItem("Hand Guard/Gloves L", "Protector", 120000, 30000, R.drawable.handsprotector)

        dbHelper.insertItem("Legs Guard S", "Protector", 110000, 25000, R.drawable.legsprotector)
        dbHelper.insertItem("Legs Guard M", "Protector", 110000, 25000, R.drawable.legsprotector)
        dbHelper.insertItem("Legs Guard L", "Protector", 110000, 25000, R.drawable.legsprotector)

        dbHelper.insertItem("Foot Guard/Gloves S", "Protector", 120000, 30000, R.drawable.footprotector)
        dbHelper.insertItem("Foot Guard/Gloves M", "Protector", 120000, 30000, R.drawable.footprotector)
        dbHelper.insertItem("Foot Guard/Gloves L", "Protector", 120000, 30000, R.drawable.footprotector)

        dbHelper.insertItem("Shincai Groin Guard Male", "Protector", 110000, 25000, R.drawable.shincai)
        dbHelper.insertItem("Shincai Groin Guard Female", "Protector", 110000, 25000, R.drawable.shincai)

        dbHelper.insertItem("Mouth Guard/Gamsil Single", "Protector", 110000, 25000, R.drawable.gamsil)
        dbHelper.insertItem("Mouth Guard/Gamsil Double", "Protector", 110000, 25000, R.drawable.gamsil)

        // ALAT LATIHAN
        dbHelper.insertItem("Target Kick Single", "Alat Latihan", 70000, 15000, R.drawable.targetkick)
        dbHelper.insertItem("Target Kick Double", "Alat Latihan", 70000, 15000, R.drawable.targetkick)
        dbHelper.insertItem("Sandsack", "Alat Latihan", 70000, 15000, R.drawable.sandsack)
        dbHelper.insertItem("Target Box", "Alat Latihan", 70000, 15000, R.drawable.targetbox)
        dbHelper.insertItem("Papan Break", "Alat Latihan", 90000, 20000, R.drawable.kyukpa)
        dbHelper.insertItem("Mini Cone", "Alat Latihan", 90000, 20000, R.drawable.cone)
        dbHelper.insertItem("Resistance Band", "Alat Latihan", 90000, 20000, R.drawable.band)

    }

}