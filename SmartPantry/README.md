# Smart Pantry Manager (MAD700)

Java Android application that suggests recipes based on leftover pantry ingredients to help reduce food waste.

**Student:** Richfield BSc IT — MAD700 Practical Assignment  
**Repo:** https://github.com/Shiv0130/Android-Studio (SmartPantry folder)

## Features
- **Pantry list** with RecyclerView (SQLite-backed)
- **CRUD**: add, edit (tap row), delete (long-press) ingredients
- **Form validation** (name + positive quantity required)
- **Suggested recipes** matched strictly to current pantry items
- **Recipe detail** (prep time, ingredients, step-by-step instructions)
- **Settings** with SharedPreferences (display name, notifications)
- **Bottom navigation**: Pantry | Recipes | Settings
- All user-facing text from `strings.xml` (no hardcoded UI copy)

## How to run
1. Clone the repository
2. Open the `SmartPantry` folder in Android Studio
3. Sync Gradle
4. Run on emulator or device (minSdk 23)

## Version control (lab history)
| Lab | Focus |
|-----|--------|
| 1 | Project + strings |
| 2–3 | Layout shell, RecyclerView, adapter |
| 4 | SQLite helper + load list |
| 5–6 | Add/Edit form + delete |
| 7–8 | Recipe matching, detail, Settings |
| 9 | Polish, README, submission branch |

Branch **`lab-9`** holds the complete app for merge into `main`.

## Match rule
A recipe is shown only if **every** required ingredient name appears in the pantry (case-insensitive contains match).

## Demo path
1. Open app → seeded sample items (or add your own)
2. FAB → add ingredient → validation toasts if empty/invalid
3. Tap row → edit → save
4. Long-press → delete confirm
5. Recipes tab → matched recipes or empty message
6. Tap recipe → detail
7. Settings → save name/toggle → reopen to confirm persistence
