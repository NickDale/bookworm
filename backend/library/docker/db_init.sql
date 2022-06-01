-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2022. Ápr 27. 22:43
-- Kiszolgáló verziója: 10.4.22-MariaDB
-- PHP verzió: 7.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `bookworm`
--
CREATE DATABASE IF NOT EXISTS `bookworm` DEFAULT CHARACTER SET utf16 COLLATE utf16_hungarian_ci;
USE `bookworm`;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `author`
--

CREATE TABLE `author` (
  `id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `last_mod_user` varchar(100) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `last_mod_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_hungarian_ci;

--
-- A tábla adatainak kiíratása `author`
--

INSERT INTO `author` (`id`, `name`, `created_date`, `created_by`, `last_mod_user`, `last_mod_date`) VALUES
(27, 'Agatha Christie', '2022-01-11 15:45:25', 'nbalogh', NULL, NULL),
(28, 'Csonka Enikő', '2022-01-11 15:45:25', 'nbalogh', NULL, NULL),
(29, 'Amanda Yee', '2022-01-11 15:45:25', 'nbalogh', NULL, NULL),
(30, 'Kath Stathers', '2022-01-11 15:45:25', 'nbalogh', NULL, NULL),
(31, 'Rosemary Sullivan', '2022-01-11 15:45:25', 'nbalogh', NULL, NULL),
(32, 'Yamamoto Cunetomo', '2022-01-11 15:45:25', 'nbalogh', NULL, NULL),
(33, 'Fehér Krisztián', '2022-01-11 15:45:25', 'nbalogh', NULL, NULL),
(34, 'George R. R. Martin', '2022-04-23 17:13:59', 'tnandi', NULL, NULL),
(35, 'J. K. Rowling', '2022-04-23 17:18:18', 'tnandi', NULL, NULL),
(36, 'Rae Carson', '2022-04-23 17:24:43', 'tnandi', NULL, NULL),
(37, 'Lukács Ildikó', '2022-04-23 17:26:53', 'tnandi', NULL, NULL),
(38, 'Anders Hansen', '2022-04-23 17:28:39', 'tnandi', NULL, NULL),
(39, 'Beth Kempton', '2022-04-23 17:30:28', 'tnandi', NULL, NULL),
(40, 'Robbie Thompson', '2022-04-23 17:32:54', 'tnandi', NULL, NULL),
(41, 'Delilah S. Dawson', '2022-04-23 17:33:47', 'tnandi', NULL, NULL),
(42, 'Stephen King', '2022-04-23 17:35:41', 'tnandi', NULL, NULL),
(43, 'Delia Owens', '2022-04-23 17:40:31', 'tnandi', NULL, NULL),
(44, 'Susanne Schötz', '2022-04-23 17:41:47', 'tnandi', NULL, NULL),
(45, 'Juan Pablo Escobar', '2022-04-23 17:42:56', 'tnandi', NULL, NULL),
(46, 'Saifedean Ammous', '2022-04-23 17:44:05', 'tnandi', NULL, NULL),
(47, 'Szunyoghy András', '2022-04-23 17:45:22', 'tnandi', NULL, NULL),
(48, 'Samin Nosrat', '2022-04-23 17:46:27', 'tnandi', NULL, NULL),
(49, 'Szentgyörgyi Krisztina', '2022-04-23 17:47:33', 'tnandi', NULL, NULL),
(50, 'James Longo', '2022-04-23 17:48:39', 'tnandi', NULL, NULL),
(51, 'Ellie Mackin Roberts', '2022-04-23 17:50:17', 'tnandi', NULL, NULL),
(52, 'Lengyel Tamás', '2022-04-23 17:51:25', 'tnandi', NULL, NULL),
(53, 'Kevin J. Anderson', '2022-04-23 17:55:31', 'tnandi', NULL, NULL),
(54, 'Pataki János', '2022-04-23 17:56:50', 'tnandi', NULL, NULL),
(55, 'Jody Houser', '2022-04-23 17:58:16', 'tnandi', NULL, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `book`
--

CREATE TABLE `book` (
  `id` int(11) NOT NULL,
  `title` varchar(200) COLLATE utf16_hungarian_ci NOT NULL,
  `isbn` varchar(50) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `img_link` varchar(500) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `short_description` varchar(2000) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `loanable` tinyint(1) NOT NULL DEFAULT 1,
  `max_loan_days` int(11) NOT NULL DEFAULT 0,
  `category_id` int(11) NOT NULL,
  `barcode` varchar(50) COLLATE utf16_hungarian_ci NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `last_mod_user` varchar(100) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `last_mod_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_hungarian_ci;

--
-- A tábla adatainak kiíratása `book`
--

INSERT INTO `book` (`id`, `title`, `isbn`, `img_link`, `short_description`, `loanable`, `max_loan_days`, `category_id`, `barcode`, `created_date`, `created_by`, `last_mod_user`, `last_mod_date`) VALUES
(18, 'Halál a Níluson', '9789634795353', 'https://s02.static.libri.hu/cover/65/6/6704876_5.jpg', 'A Halál a Níluson a szerző talán legzseniálisabb regénye, melyben a híres belga nyomozó, Hercule Poirot épp a szabadságát tölti egy luxushajón. A tökéletesnek induló hajóút hamar tragédiába torkollik, és a \"véletlenül\" épp a közelben tartózkodó Poirot önkéntelenül is nyomozásba kezd.', 1, 10, 1, '9789634795353', '2022-01-11 00:00:00', '', NULL, NULL),
(19, 'Szerelmi bűnügyek', '9789634798330', 'https://s02.static.libri.hu/cover/f7/2/8299108_5.jpg', 'A szerelem furcsa dolgokra képes. Néha a legjobbat, olykor pedig a legrosszabbat hozza ki belőlünk. Agatha Christie, az emberi lélek páratlan ismerője e kötet novelláiban is mesterien fonja szerelmi bűnügyeinek szálait. Kedvenc detektíveinkkel meglepő helyszíneken találkozhatunk - Mr. Satterthwaite az operába látogat Mr. Harley Quinnel,', 1, 10, 1, '9789634798330', '2022-01-11 00:00:00', '', NULL, NULL),
(20, 'Örök éj', '9789634797814', 'https://s05.static.libri.hu/cover/6c/d/4880554_5.jpg', 'Az Örök éj Agatha Christie személyes kedvence saját művei közül, feszültséggel teli dráma, melynek fordulatai még a krimikirálynőtől is szokatlanul borzongatóak.', 1, 20, 1, '9789634797814', '2022-01-11 00:00:00', '', NULL, NULL),
(21, 'Az alibi', '9789634798248', 'https://s05.static.libri.hu/cover/33/9/3930347_5.jpg', 'A bíróság szerint Jacko Argyle egy piszkavassal brutálisan agyonverte anyját. Tettéért életfogytiglani börtön vár rá, ám nem raboskodik sokáig: tüdőgyulladás viszi el. Ekkor bukkan fel dr. Arthur Calgary, a fiatal kutató, aki épp most tért vissza antarktiszi expedíciójáról, és azt állítja, hogy Jacko biztosan ártatlan volt.', 1, 20, 1, '9789634798248', '2022-01-11 00:00:00', '', NULL, NULL),
(22, 'Házi Pékség - macera és kovász nélkül', '9786150082202', 'https://s06.static.libri.hu/cover/3a/4/8309144_5.jpg', 'Semmi macera, semmi kovasz! Ahogy azt tőlünk már megszokhattátok, ismét egy olyan receptgyűjteménnyel érkezünk melynek minden egyes receptjét könnyedén el tudtok készíteni! Választásunk ezúttal a péksüteményekre esett, kiegészítve egy-két gyorsan elkészíthető süteménnyel! ', 1, 14, 12, '9786150082202', '2022-01-11 00:00:00', '', NULL, NULL),
(23, 'Jóbarátok - A hivatalos szakácskönyv', '9786155915727', 'https://s03.static.libri.hu/cover/14/a/6930868_5.jpg', '\"A kőkemény Jóbarátok-rajongóknak muszáj beszerezniük!\" - POPSUGAR Trombitáld össze a jóbarátokat, és üdvözöljétek együtt ezt a több mint 100 receptet (\"Na, mi a helyzet?\"), amelyet hőn szeretett tévéműsorotok ihletett. Akár minden hájjal megkent chef vagy, mint Monica Geller, akár most indítasz catering céget, mint Phoebe Buffay, ', 1, 17, 12, '9786155915727', '2022-01-11 00:00:00', '', NULL, NULL),
(24, 'Bakancslista', '9789632448114', 'https://s01.static.libri.hu/cover/56/1/4331188_4.jpg', 'Egész életünkben álmodozunk. Hétévesen szeretnénk kedvenc énekesünk mellett állni a színpadon, tizenhét évesen pedig már Kiotó cseresznyefái alatt sétálgatnánk. Be nem teljesült vágyaink listáját - a 2007-ben bemutatott Bakancslista című mozifilm hatására - ma már bakancslistának nevezzük. ', 1, 7, 5, '9789632448114', '2022-01-11 00:00:00', '', NULL, NULL),
(25, '500 inspiráló túra a Föld körül', '9789635444908', 'https://s03.static.libri.hu/cover/8f/8/7557267_5.jpg', 'Öt kontinenst bebarangoló, rendhagyó útiköny, amelyben összesen 500 túra szerepel az országokon átívelő zarándoklatoktól kezdve a különleges városrészekben tett kisebb sétákig. Minden egyes túrát írók, művészek és zenészek élete vagy művei insprálták. Egy-egy túra keretében megismerhetjük a különféle műveket ihlető helyszíneket.', 0, 7, 5, '9789635444908', '2022-01-11 00:00:00', '', NULL, NULL),
(26, 'Nyomozás Anne Frank ügyében - Az árulás igaz története', '9786155783746', 'https://s02.static.libri.hu/cover/84/e/8199376_5.jpg', 'Ki és miért árulta be Anne Frankot, valamint a családját? A teljes titoktartás mellett megírt és a világon egy időben megjelenő könyv lapjain végre fény derül a jól őrzött titokra. Több mint harmincmillióan olvasták a naplót, amelybe a tizenéves Anne a legtitkosabb gondolatait jegyezte be, miközben családjával és négy másik emberrel egy padlástérben', 1, 13, 15, '9786155783746', '2022-01-11 00:00:00', '', NULL, NULL),
(27, 'Hagakure - A szamurájok kódexe', '9789634793717', 'https://s06.static.libri.hu/cover/42/5/7015852_5.jpg', 'A Hagakure a Szamuráj Útjára, a Busidóra vonatkozó rövid tanítások, történetek és feljegyzések gyűjteménye a XVIII. sz. elejéről, amelyet a később zen szerzetessé vált szamuráj, Jamamoto Cunetomo diktált le ifjú tanítványának. ', 1, 13, 15, '9789634793717', '2022-01-11 00:00:00', '', NULL, NULL),
(28, 'Androidos alkalmazásfejlesztés Kotlin nyelven - 3. rész', '9786156184184', 'https://s01.static.libri.hu/cover/37/8/8115870_4.jpg', 'Okostelefonos vektorgrafika programozása sokak fantáziáját megmozgatja, számos alkalmazáshoz adhat ötletet, új perspektívát nyithat, pláne, ha egy csipetnyi interakcióval is fel tudjuk ruházni mindezt', 1, 13, 13, '9786156184184', '2022-01-11 00:00:00', '', NULL, NULL),
(29, 'Androidos alkalmazásfejlesztés Kotlin nyelven - 2. rész', '9786156184054', 'https://s01.static.libri.hu/cover/49/f/6705540_4.jpg', 'Az androidos eszközök egyik legizgalmasabb lehetősége, hogy különféle szenzorokkal és nagyon komoly helymeghatározó képességgel rendelkeznek. Ezen lehetőségek programozása egyáltalán nem nehéz, csak tudni kell, hol kezdje az ember. Ez a kiadvány pontosan ebben nyújt segítséget', 1, 13, 13, '9786156184054', '2022-01-11 00:00:00', '', NULL, NULL),
(30, 'Navigációs szoftverek fejlesztése Androidra', '9786155477119', 'https://s02.static.libri.hu/cover/90/7/1180983_5.jpg', 'A könyv az Android grafikai programozása mellett elsősorban a mobileszközök egy speciális részegységének, a GPS vevőegységnek a gyakorlati használatát mutatja be és a navigációs szoftverek, valamint a digitális térképek készítésébe nyújt betekintést. Több fő témakört is bemutatunk, így a GPS koordináták felhasználását helyzetalapú alkalmazásokban,', 1, 13, 15, '9786155477119', '2022-01-11 00:00:00', '', NULL, NULL),
(32, 'Navigációs szoftverek fejlesztése Androidra', '9786155477119', 'https://s02.static.libri.hu/cover/90/7/1180983_5.jpg', 'A könyv az Android grafikai programozása mellett elsősorban a mobileszközök egy speciális részegységének, a GPS vevőegységnek a gyakorlati használatát mutatja be és a navigációs szoftverek, valamint a digitális térképek készítésébe nyújt betekintést. Több fő témakört is bemutatunk, így a GPS koordináták felhasználását helyzetalapú alkalmazásokban,', 1, 13, 15, '978615547711922', '2022-01-11 00:00:00', '', NULL, NULL),
(33, 'Királyok csatája - A tűz és jég dala II.', '9789634478430', 'https://s03.static.libri.hu/cover/5b/a/6708944_5.jpg', 'Westeros földjére ősz borul, az eget kettészelő üstökös vérontást és nyomorúságot jósol. Sárkánykő komor falaitól egészen Deres rideg földjéig seregek gyülekeznek; céljuk a Vastrón és a hét királyság fölötti uralom megszerzése. A Starkok bosszúra szomjaznak, ám a Lannisterek elsöprő vagyonával és haderejével kell szembenézniük, míg a Baratheon-házban fivér fordul fivér ellen.', 1, 20, 1, '9789634478430', '2022-04-23 17:16:30', 'tnandi', NULL, NULL),
(34, 'Fire and Blood', '9780008307738', 'https://s06.static.libri.hu/cover/2a/1/4841411_5.jpg', 'Set 300 years before the events in A Song of Ice and Fire, Fire and Blood is the definitive history of the Targaryens in Westeros as told by Archmaester Gyldayn, and chronicles the conquest that united the Seven Kingdoms under Targaryen rule through the Dance of the Dragons: the Targaryen civil war that nearly ended their dynasty forever.', 1, 21, 1, '9780008307738', '2022-04-23 17:17:09', 'tnandi', NULL, NULL),
(35, 'Sárkányok tánca - A tűz és jég dala V.', '9789634478522', 'https://s03.static.libri.hu/cover/f1/3/6708941_5.jpg', 'Közeleg a tél. A hideg szelek feltámadtak a sokat szenvedett Hét Királyságban, ahol az öt király háborúja után a túlélőknek most az éhínséggel kell szembenézniük. Az emberek birodalmát védelmező Fal ifjú parancsnoka, Havas Jon a Mások elleni reménytelen küzdelemre készíti fel a szétzüllött Éjjeli Őrséget, ám rá kell döbbennie, hogy ellenségei jóval közelebb vannak hozzá, mint gondolná. Stannis Baratheon Észak uralmáért vív elkeseredett harcot a Boltonokkal, miközben Királyvárban a Lannister-ház próbálja megerősíteni Tommen, a gyermekkirály törékeny uralmát a kivérzett Hét Királyság fölött. A Keskeny-tenger másik oldalán Tyrion Lannister, a megvetett és üldözött rokongyilkos sárkányvadászatra indul, ám útja veszélyekkel és váratlan kitérőkkel teli. A világ eközben az ősi városra, Meereenre figyel, ahol Viharbanszületett Daeneryst, Westeros jog szerinti uralkodóját minden oldalról ellenségei szorongatják. Hogy arathat diadalt a Sárkányok Anyja, ha három gyermekére sem számíthat? A végkifejlet csak tűz és vér lehet, ám ki éli túl a sárkányok táncát?', 1, 18, 1, '9789634478522', '2022-04-23 17:17:57', 'tnandi', NULL, NULL),
(36, 'Harry Potter és a bölcsek köve', '9789633246986', 'https://s05.static.libri.hu/cover/67/4/3511674_5.jpg', 'Harry Potter tizenegy éves, amikor megtudja, hogy ő bizony varázslónak született, és felvételt nyert a Roxfort Boszorkány- és Varázslóképző Szakiskolába. Az új iskolában és a csupa újdonsággal szolgáló varázslóvilágban töltött első tanév kemény erőpróba a számára. Ráadásul nem csupán a vizsgákon kell megfelelnie, de egy életre-halálra szóló küzdelemnek is részese lesz.', 1, 15, 1, '9789633246986', '2022-04-23 17:19:00', 'tnandi', NULL, NULL),
(37, 'Harry Potter és a Titkok Kamrája', '9789633247341', 'https://s03.static.libri.hu/cover/14/f/3511849_5.jpg', 'Harry Potter, aki éppen egy éve tudta meg magáról, hogy varázslónak született, Dobby figyelmeztetése ellenére tovább folytatja tanulmányait a Roxfort Boszorkány- és Varázslóképző Szakiskolában. Mivel lekési a különvonatot, barátjával, Ronnal együtt egy repülő autón érkezik tanulmányai színhelyére. S a java csak ezután következik! Hamarosan bebizonyosodik, hogy Dobby nem a levegőbe beszélt, amikor óvni próbálta őt.', 1, 16, 14, '9789633247341', '2022-04-23 17:19:40', 'tnandi', NULL, NULL),
(38, 'Harry Potter és az azkabani fogoly', '9789633247396', 'https://s03.static.libri.hu/cover/3f/b/3511851_5.jpg', 'Harry Potter szokásos rémes vakációját tölti Dursley-éknél, ám a helyzet úgy elfajul, hogy Harry elviharzik a Privet Drive-ról. Így köt ki a Kóbor Grimbuszon, ami elviszi őt abba a világba, ahová egész nyáron vágyott. Az Abszol úton ijesztő hírek járják: az Azkabanból, a gonosz varázslókat őrző rettegett börtönből megszökött egy fogoly. A Mágiaügyi Minisztériumban tudják, hogy a veszélyes szökevény a Roxfort Boszorkány- és Varázslóképző Szakiskolába tart. Harry pedig egy véletlen folytán tudomást szerez róla, hogy az illető az ő nyomát követi. ', 1, 16, 14, '9789633247396', '2022-04-23 17:20:25', 'tnandi', NULL, NULL),
(39, 'Harry Potter és a Tűz Serlege', '9789633246993', 'https://s03.static.libri.hu/cover/c2/e/3511852_5.jpg', 'Harry Potter vakációja a szokásosnál jobbnak ígérkezik: részt vehet a varázsvilág legnagyobb sportünnepén, a Kviddics Világkupán. Ám az egybegyűltek szórakozása korántsem alakul felhőtlenül: baljós történések zavarják meg az eseményt. A Roxfort Boszorkány- és Varázslóképző Szakiskolába visszatérő diákok azonban hamar elfeledkeznek a meccsről, mivel kiderül, hogy ez évben a Roxfortban rendezik meg a Trimágus Tusát. Harry csak szurkolni készül a bajnokoknak, ám váratlanul maga is köztük találja magát. A próbák életveszélyesek, így gyanítható, hogy aki tudtán kívül benevezte Harryt a tusára, az bizony az életére tör.', 1, 18, 14, '9789633246993', '2022-04-23 17:22:07', 'tnandi', NULL, NULL),
(40, 'Harry Potter és a Főnix Rendje', '9789633244418', 'https://s03.static.libri.hu/cover/d0/9/3511853_5.jpg', 'Harry Potter nem hitte volna, hogy egyszer ő fogja megvédeni basáskodó unokatestvérét, Dudley-t. Ám amikor fényes nappal dementorok támadnak kettőjükre, ez történik. De számos más vészjósló esemény is mutatja, hogy a varázsvilág békéjét sötét erők fenyegetik.\r\nHarry nincs egyedül az ellenük vívott küzdelemben: a Főnix Rendje egy titkos főhadiszálláson szervezi a Sötét Nagyúr elleni harcot, ami minden fronton zajlik. Harry például kénytelen különórákat venni Piton professzortól, hogy ki tudja védeni Voldemort erőszakos behatolásait a tudatába', 1, 19, 14, '9789633244418', '2022-04-23 17:22:48', 'tnandi', NULL, NULL),
(41, 'Harry Potter és a Félvér Herceg', '9789633247365', 'https://s03.static.libri.hu/cover/fe/e/3511854_5.jpg', 'A Voldemort elleni harc állása aggasztó; a baljós jeleket már a muglikormány is észleli. Szaporodnak a rejtélyes halálesetek, katasztrófák. Harry azt gyanítja, hogy esküdt ellensége, Draco Malfoy is a halálfalók jelét viseli. Az élet azonban háborús időkben sem csak harcból áll. A Weasley-ikrek üzleti tevékenysége egyre kiterjedtebb. Szerelmek szövődnek a felsőbb évesek között, a Roxfort házai pedig továbbra is versengenek egymással. Harry Dumbledore segítségével igyekszik minél alaposabban megismerni Voldemort múltját, ifjúságát, hogy rátaláljon a Sötét Nagyúr sebezhető pontjára.', 1, 20, 14, '9789633247365', '2022-04-23 17:23:33', 'tnandi', NULL, NULL),
(42, 'Harry Potter és a Halál ereklyéi', '9789633247358', 'https://s03.static.libri.hu/cover/f2/8/3511856_5.jpg', 'Amikor a tizenhetedik évét betöltő Harry ezúttal Hagrid motorjának oldalkocsijában utoljára hagyja el a Privet Drive-ot, az őt védő bűbáj szertefoszlik, és a halálfalók azonnal a nyomába erednek. A Főnix Rendje azon fáradozik, hogy biztos helyre szöktesse őt, csakhogy Harrynek a bujkáláson kívül egyéb tervei is vannak: Dumbledore professzortól kapott feladatát kell végrehajtania. Így a tanév nélküle kezdődik el a Roxfortban. Vajon sikerül-e a folytonos életveszély közepette teljesítenie a küldetést, amitől a végső összecsapás kimenetele függ?', 1, 21, 14, '9789633247358', '2022-04-23 17:24:16', 'tnandi', NULL, NULL),
(43, 'Star Wars: Skywalker kora', '9789634975946', 'https://s05.static.libri.hu/cover/b3/d/6593691_5.jpg', 'Rae Carson a Skywalker kora regényváltozatában olyan titkokról is lerántja a leplet, melyek kimaradtak filmből, és régóta izgatják a nézők és az olvasók fantáziáját. A kivágott jelenetek és a kibővített cselekményszálak új megvilágításba helyezik az epikus történet befejezését.', 1, 20, 17, '9789634975946', '2022-04-23 17:25:28', 'tnandi', NULL, NULL),
(44, 'Esőcsepp keltette hullámok', '9786155637193', 'https://s03.static.libri.hu/cover/df/a/8059360_5.jpg', 'Képzeljük magunk elé a következő helyzetet: éppen arról értesítenek minket, hogy a családunkba érkezett új jövevény siketen és vakon született. Próbáljuk meg megfogalmazni magunkban, hogy mik lennének az első gondolataink, mit éreznénk az első percekben, órákban. Tudnánk vajon egyáltalán, hogy mit is jelent ez? Vagy megdöbbennénk, és nehezünkre esne az egészet elképzelni?', 1, 21, 3, '9786155637193', '2022-04-23 17:27:32', 'tnandi', NULL, NULL),
(45, 'Edzett agy - Hogyan növeli az agyad teljesítőképességét a mozgás?', '9786150064598', 'https://s02.static.libri.hu/cover/8d/5/5845273_5.jpg', 'Szorítsd ökölbe mindkét kezed, és tedd őket össze. Ekkora az agyunk. És mennyit nyom? Nagyjából, mint egy liter tej. Gondolj csak bele: minden, amit valaha éreztél vagy átéltél, ilyen kis helyen elfér! Sőt: az összes tulajdonságod, tudásod és emléked is. Míg testünk többi szervének mechanizmusait jó ideje ismerjük, agyunk sokáig rejtély maradt.\r\nAz utóbbi néhány évtized új módszereinek köszönhetően azonban az agykutatásban is hatalmas áttörést sikerült elérni. Kezdjük megérteni, pontosan hogyan működik az emberi agy, és bátran kijelenthetjük, nem csupán egy szerv a sok közül: az agyunk mi magunk vagyunk.\r\nAgyunk egészsége szempontjából kevés dolog fontosabb, mint a testmozgás.\r\nLesz egy plusz ,,agyi sebességünk\", amibe például akkor kapcsolhatunk, ha nagy káoszban kell figyelnünk, vagy nyugodtnak kell maradnunk annak ellenére, hogy a tekervényeink maximális sebességen forognak. Ráadásul, a testmozgástól okosabbak is leszünk. Furcsán hangzik, ugye?\r\nEbben a könyvben elmondom, mit művel agyunkkal a testmozgás. Kiderül, miért érezhető néhány pozitívum már egy-egy séta vagy futás után, míg más hatások csak hosszabb, legalább egyéves rendszeres edzést követően jelentkeznek.', 1, 23, 2, '9786150064598', '2022-04-23 17:29:16', 'tnandi', NULL, NULL),
(46, 'Wabi Sabi - Útmutató a belső békéhez', '9789634339991', 'https://s03.static.libri.hu/cover/33/a/5168264_5.jpg', 'A wabi sabi a japán esztétika egyik irányzata, amely segít, hogy megtaláljuk a szépséget a tökéletlenségben, és értékeljük az egyszerűséget, illetve a természetességet.\r\nA zenben gyökerező wabi sabi bölcsessége különösen fontos a modern korban, amikor folyamatosan keressük az élet anyagi világon túlmutató értelmét és belső békénket.\r\nA wabi sabi a maga évezredes bölcsességével segít lelassítani, és megmutatja, hogyan kapcsolódhatunk újra a természethez. Legyen szó az évszakok váltakozásban való gyönyörködésről, a hívogató otthon megteremtéséről vagy akár a méltósággal teli öregedésről, ez az ősi japán megközelítés és a könyvben található gyakorlati tanácsok segítenek, hogy tökéletesen tökéletlen életünk örömteli és tartalmas legyen.', 1, 23, 2, '9789634339991', '2022-04-23 17:31:43', 'tnandi', NULL, NULL),
(47, 'Pókfej 1.', '9789635950218', 'https://s05.static.libri.hu/cover/f9/3/8568059_5.jpg', 'Azt hiszed, mindent tudsz Peter Parker korai pályafutásáról? Csak hiszed!\r\nAkció, kaland és eszeveszett vadulás vár Pókfej vadonatúj képregényében - klasszikus Marvel-stílusban!\r\nA hálószövő csodapók fiatalkorát átszövő kalandokban visszatérünk az utolsó pillanatban megírt házi feladatok, a lányokkal (vagy bárki mással) való kínos beszélgetések és a minden sarkon leskelődő veszélyek idejébe. Tanúja lehetsz Pókfej legelső összecsapásának OKI DOKIVAL, HOMOKEMBERREL, és találkozhatsz számos, váratlan vendégszereplővel is.', 1, 10, 17, '9789635950218', '2022-04-23 17:33:32', 'tnandi', NULL, NULL),
(48, 'Pókember: Venom - Marvel akcióhősök', '9789635950027', 'https://s05.static.libri.hu/cover/27/1/7995043_5.jpg', 'Ismét új pókember tűnik fel a színen, ám a jövevény nagyobb, gonoszabb, és ámokfutást rendez a városban. A Marvel-akcióhősök sorozatban bemutatkozik Venom!\r\nA fiatalabb olvasóknak szóló, akciódús kalandok folyatódnak Peter Parker, Gwen Stacy és Miles Morales főszereplésével! Amikor Szellempók először keresztezi Venom útját, felmerül a kérdés: vajon az új pókfej vakmerő barát vagy ádáz ellenség?\r\nDelilah S. Dawson író és Davide Tinto rajzoló humorban és izgalomban bővelkedő történetéből kiderül.', 1, 12, 17, '9789635950027', '2022-04-23 17:34:20', 'tnandi', NULL, NULL),
(49, 'Végítélet', '9789634058960', 'https://s01.static.libri.hu/cover/0e/f/4832424_5.jpg', 'Valahol a kaliforniai sivatag mélyén, egy titkos katonai laboratóriumban üzemzavar támad, s elszabadul egy gyilkos vírus. Az emberiség csupán egy kis csoportja éli túl a kórt. A túlélők két táborra oszlanak: vannak, akik Abagail anyát, a 108 éves idős asszonyt, és vannak, akik a baljós, sötét lényt, Randall Flagget követik. Míg az egyik csoport a társadalom újrafelépítésén fáradozik, a másik fegyverkezik és katonákat képez ki. Hamarosan elkerülhetetlenné válik az összecsapás. Stephen King klasszikussá vált monumentális regénye maga a figyelmeztetés: \"Emberiség, vigyázz!\" ', 1, 25, 1, '9789634058960', '2022-04-23 17:36:53', 'tnandi', NULL, NULL),
(50, 'Álom doktor', '9789635041329', 'https://s01.static.libri.hu/cover/8c/b/5779625_5.jpg', 'A ragyogás folytatásában a gonosz ismét visszatér közénk. A kisfiú, aki annak idején annyi rémséget élt át a Panoráma Szállóban, immár felnőttként, miután kigyógyult alkoholbetegségéből, egy New Hampshire-i hospice-házban dolgozik ápolóként, ahol a betegek Álom doktornak kezdik szólítani. Dan Torrance különleges képessége felnőttkorában is fontos szerephez jut. Régóta kísértő szellemek, az Igaz Kötés nevű baljós, vándorló kompánia és egy Abra nevű, még Danénél is erősebb ragyogással bíró kislány körül bonyolódik a lidérces, borzongató történet.', 1, 23, 1, '9789635041329', '2022-04-23 17:37:37', 'tnandi', NULL, NULL),
(51, 'A ragyogás', '9789634059165', 'https://s01.static.libri.hu/cover/5c/9/4832423_5.jpg', ' Minden nagy szállodának vannak botrányai - mondta. - Ahogy kísértet is van minden nagy szállodában. Hogy miért? A fenébe is, az emberek jönnek-mennek. Megesik, hogy valamelyik a szobájában dobja fel a talpát, a szíve, vagy gutaütés, vagy valami ilyesmi. A szálloda babonás hely. Nincs tizenharmadik emelet, nincs tizenhármas szoba, nincs tükör az ajtón, amin az ember bemegy, meg hasonlók.\"', 1, 25, 1, '9789634059165', '2022-04-23 17:38:13', 'tnandi', NULL, NULL),
(52, 'Kedvencek temetője', '9789635040094', 'https://s01.static.libri.hu/cover/8a/e/5133250_5.jpg', 'Dr. Louis Creed, a fiatal orvos kitűnő állást kapott: a Maine-i Egyetem rendelőjének lett a vezetője, ezért Chicagóból az idilli New England-i tájban álló, magányos házba költözik családjával - feleségével, Rachellel, ötéves lányukkal, Ellie-vel és másfél éves kisfiukkal, Gage-dzsel. Boldogan, a szép jövő reményében veszik birtokukba új otthonukat...', 1, 15, 1, '9789635040094', '2022-04-23 17:39:20', 'tnandi', NULL, NULL),
(53, 'Ahol a folyami rákok énekelnek', '9789636040581', 'https://s01.static.libri.hu/cover/22/f/6318936_5.jpg', 'Delia Owens regénye Észak-Karolina ritkán lakott, mocsaras partvidékén játszódik az 1950-es és \'60-as években. A történet főhőse a lápvidéken sorsára hagyott kislány, Kya Clark, aki az évek során elszigeteltségében önellátásra rendezkedik be, s alig érintkezik a környékbeliekkel.', 1, 21, 1, '9789636040581', '2022-04-23 17:41:14', 'tnandi', NULL, NULL),
(54, 'A macskák titkos nyelve', '9789634755746', 'https://s03.static.libri.hu/cover/f1/9/8596598_5.jpg', 'Gondolkoztál rajta valaha, hogy vajon mit is mond a macskád?\r\nA macskák nem csak összevissza nyávognak, és nem azért fújnak vagy morognak, mert nincs jobb dolguk. Szándékkal adnak ki különböző hangokat, és fontos információkat közölnek általuk, akár velünk, akár a fajtársaikkal.', 1, 26, 11, '9789634755746', '2022-04-23 17:42:27', 'tnandi', NULL, NULL),
(55, 'Apám, a drogbáró', '9789634799085', 'https://s04.static.libri.hu/cover/cc/9/3495058_5.jpg', 'Juan Pablo Escobar, jelenlegi nevén Juan Sebastián Marroquín Santos a világtörténelem legismertebb drogkereskedőjének a fia. Medellínben született 1977-ben. Építész és ipari tervezőmérnök. Mint pacifista, párbeszédet és megbékélést kezdeményezett azokkal, akik a nyolcvanas és kilencvenes években lettek apja drogkereskedelemmel kapcsolatos terrorizmusának áldozatai. Gyakran tart előadásokat, szerepelt az ENSZ Nemzetközi Békenapján bemutatott, díjnyertes dokumentumfilmben, amely „Apám bűnei” (Pecados de mi padre) címen jelent meg 2009-ben.', 1, 42, 8, '9789634799085', '2022-04-23 17:43:35', 'tnandi', NULL, NULL),
(56, 'Bitcoin Standard - A központi bankok decentralizált alternatívája', '9789635091195', 'https://s03.static.libri.hu/cover/79/4/6489481_5.jpg', 'Amikor egy álnév mögé bújó programozó bemutatott egy új elektronikus készpénzrendszert egy jelentéktelen levelezőlistán 2008-ban, csupán nagyon kevesen figyeltek oda rá. Tíz év elteltével ez a semmiből jött, független, decentralizált szoftver minden várakozás ellenére a modern központi bankok alternatívája lett. Ismerjük meg a Bitcoin felemelkedésének történetét, tulajdonságait, amelyek lehetővé tették gyors növekedését, és várható gazdasági, politikai és társadalmi hatásait!', 1, 30, 9, '9789635091195', '2022-04-23 17:44:41', 'tnandi', NULL, NULL),
(57, 'Rajz ABC kezdőknek és haladóknak', '9789630977388', 'https://s03.static.libri.hu/cover/ca/e/885576_5.jpg', 'A rajz a természet jelenségeit, művészi vagy műszaki alkotások képét örökíti meg ceruzával vagy más megfelelő anyaggal. A rajztudás hosszú folyamat eredménye. Nem elegendő hozzá a tehetség, kell a napi szorgos gyakorlás is. Szunyoghy András könyve megmutatja, hogyan kell gondolkodni, miket kell nézni és mérni akkor, amikor rajzolunk, azaz a kezdő lépéseket magyarázza el. Segítségével megtanulhat rajzolni mindenki, aki erre belső késztetést érez.', 1, 12, 10, '9789630977388', '2022-04-23 17:45:57', 'tnandi', NULL, NULL),
(58, 'Só, zsír, sav, hő - A jó főzés négy eleme', '9789633558966', 'https://s05.static.libri.hu/cover/61/1/4922257_5.jpg', 'Samin Nosrat másodéves egyetemistaként került kapcsolatba a főzéssel, amikor Kalifornia egyik emblematikus éttermében, a Chez Panisse-ban kezdett dolgozni, ahol a ranglétra összes fokát végigjárva séf lett. Azóta a főzés a szenvedélye, akármerre járt a világban, Japántól Mexikóig, Olaszországtól Amerikáig az ízek titkát kutatta, és azt tapasztalta, hogy az ételt négy elem tesz tökéletessé vagy tesz tönkre: a só, a zsír, a sav vagy a hő.', 1, 12, 12, '9789633558966', '2022-04-23 17:46:59', 'tnandi', NULL, NULL),
(59, 'Orvosi latin kifejezések - Egészségügyi ágazatban tanulóknak', '9789636978679', 'https://s03.static.libri.hu/cover/e8/e/8285925_5.jpg', 'Kiadványunk az egészségügyi szakképzésben tanulók és az egészségügyi főiskolai (BSc) képzésekben részt vevők számára kíván segítséget nyújtani az orvosi latin kifejezések elsajátításában. A könyvben több mint 1300, tematikusan összerendezett orvosi latin kifejezés található, amelyeket a mindennapi gyakorlatban a leggyakrabban használnak az egészségügyi ágazatban tanulók és dolgozók.', 1, 56, 13, '9789636978679', '2022-04-23 17:48:14', 'tnandi', NULL, NULL),
(60, 'Hitler és a Habsburgok - A Führer bosszúja az osztrák királyi család ellen', '9789635040957', 'https://s03.static.libri.hu/cover/46/2/6080357_5.jpg', ' Hitler és a Habsburgok a huszadik század nagy háborúinak kevésbé ismert, ám annál izgalmasabb aspektusát mutatja be.\r\nHogyan és mitől alakult ki Adolf Hitler megszállott gyűlölete a Habsburg-család iránt? Miért fogadott bosszút az eltűnőben lévő birodalom, valamint a halott főherceg és a királyi árvák ellen? A kötetből az is kiderül, hogy mit kellett átélniük azoknak a gyermekeknek, akiknek a vére a nácik szerint túlságosan kék volt, de a Habsburgok szerint mégsem eléggé.', 1, 45, 15, '9789635040957', '2022-04-23 17:49:15', 'tnandi', NULL, NULL),
(61, 'Olümposzi hősnők', '9789635446995', 'https://s03.static.libri.hu/cover/59/8/8519936_5.jpg', 'Ravaszok, csábítóak, rettentőek, erényesek - akár isteni lények, akár halandók, a nők alapjaiban formálják a görög mitológiát, mégis régóta elhomályosítják őket a férfialakok. Ideje, hogy az ő történeteik is napvilágra kerüljenek!', 1, 24, 16, '9789635446995', '2022-04-23 17:50:53', 'tnandi', NULL, NULL),
(62, 'A legszebb magyar népmesék - Hangoskönyv', '9789635447909', 'https://s03.static.libri.hu/cover/64/7/8519937_5.jpg', 'Hangoskönyvünk meséit gazdag népmesekincsünk gyöngyszemeiből válogattuk. Azokat az emblematikus műveket gyűjtöttük csokorba, melyek ma már klasszikussá nemesedtek, s nemzedékek meseélményének váltak meghatározó darabjaivá.', 1, 3, 18, '9789635447909', '2022-04-23 17:52:17', 'tnandi', NULL, NULL),
(63, 'Dűne: Atreides-ház - Második kötet', '9789635662692', 'https://s05.static.libri.hu/cover/30/2/8426437_5.jpg', '\r\nLeto Atreides egy kitörni készülő lázadásról ad hírt Ix urainak, akik azonban döntéseikkel a bolygó minden lakóját veszélybe sodorják. Shaddam koronaherceg ármánya pedig ezzel beérni látszik...\r\n\r\nEközben Pardot Kynes édeni álma a virágzóvá tett sivatagbolygóról terjedni kezd a Dűne őslakói, a fremenek között, akikben azonban gyanú ébred, milyen motivációk is hajtják a császári planetológust... és hogy valóban képes-e véghez vinni nagyszabású terveit.', 1, 23, 4, '9789635662692', '2022-04-23 17:56:18', 'tnandi', NULL, NULL),
(64, 'Délvidék - Magyarország kapuja', '9786150137285', 'https://s03.static.libri.hu/cover/eb/1/8574423_5.jpg', 'A Pataki János nevével fémjelzett könyvsorozat (Partium, Felvidék, Székelyföld, Őrvidék, Délvidék) célja, hogy bemutassa a trianoni döntéssel elszakított egykori magyar területeket. Jelen kötet segítségével Délvidéknek, a régi Magyarország déli határterületének mesés tájain barangolhatunk. Színes, képekben gazdag leírásai önmagukban élvezetes olvasmányt nyújtanak a Délvidék településeinek történetéről és nevezetességeiről, így hasznos tippeket ad kirándulások szervezéséhez is.', 1, 12, 5, '9786150137285', '2022-04-23 17:57:22', 'tnandi', NULL, NULL),
(65, 'Stranger Things: Tűzben égve', '9789634976578', 'https://s03.static.libri.hu/cover/ee/5/8455925_5.jpg', 'Évekkel azután, hogy megszöktek Dr. Brenner laboratóriumából, Marcy és Ricky még mindig próbálnak beilleszkedni a normális életbe. Amikor azonban hírét veszik, hogy a laboratórium bezárt, elindulnak megkeresni Marcy ikertestvérét, a tűzgyújtási képességgel rendelkező Kilencet. Hamarosan rájönnek, hogy barátjukat nem csak kiszabadítaniuk kell, de kimenteniük is saját elméjének rabságából, mielőtt Jamie mindent elpusztítana maga körül. És ahogy közelebb kerülnek Kilenchez, egyre több titokra jönnek rá a Dr. Brenner kisérleteivel kapcsolatban, olyan titkokra, ami mindannyiukat mélyen megrázza...', 1, 12, 17, '9789634976578', '2022-04-23 17:58:46', 'tnandi', NULL, NULL),
(66, 'Programozz C nyelven! - 2. kiadás', '9786156184177', 'https://s03.static.libri.hu/cover/50/a/8351551_5.jpg', 'A modern számítógépes programozási nyelvek egyik klasszikusa a C nyelv. Aki ezt megtanulja, lényegében bármilyen más, manapság elterjedt programozási nyelvvel boldogulni fog.', 1, 12, 13, '9786156184177', '2022-04-23 18:00:02', 'tnandi', NULL, NULL),
(67, 'Nyomtatók programozása', '9786150073330', 'https://s03.static.libri.hu/cover/59/3/6705518_5.jpg', 'A nyomtatók programozása méltatlanul az egyik leginkább elhanyagolt programozási témakör, annak ellenére, hogy számos potenciális, gyakorlati alkalmazási területe van, gondoljunk csak például a számlázásra!\r\nEz a kiadvány megmutatja, hogyan vezérelhetjük a nyomtatás folyamatát és hogyan nyomtathatunk szövegeket, grafikákat saját programjainkból. Segítségével az olvasó számos hétköznapi, vagy akár speciális nyomtatási feladatot megvalósíthat', 1, 18, 13, '9786150073330', '2022-04-23 18:01:12', 'tnandi', NULL, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `book_author`
--

CREATE TABLE `book_author` (
  `id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_hungarian_ci;

--
-- A tábla adatainak kiíratása `book_author`
--

INSERT INTO `book_author` (`id`, `author_id`, `book_id`) VALUES
(1, 27, 18),
(2, 27, 19),
(3, 27, 20),
(4, 27, 21),
(5, 28, 22),
(6, 29, 23),
(7, 30, 24),
(8, 30, 25),
(9, 31, 26),
(10, 32, 27),
(11, 33, 28),
(12, 33, 29),
(13, 33, 30),
(15, 34, 32),
(16, 34, 33),
(17, 34, 34),
(18, 34, 35),
(19, 35, 36),
(20, 35, 37),
(21, 35, 38),
(22, 35, 39),
(23, 35, 40),
(24, 35, 41),
(25, 35, 42),
(26, 36, 43),
(27, 37, 44),
(28, 38, 45),
(29, 39, 46),
(30, 40, 47),
(31, 41, 48),
(32, 42, 49),
(33, 42, 50),
(34, 42, 51),
(35, 42, 52),
(36, 43, 53),
(37, 44, 54),
(38, 45, 55),
(39, 46, 56),
(40, 47, 57),
(41, 48, 58),
(42, 49, 59),
(43, 50, 60),
(44, 51, 61),
(45, 52, 62),
(46, 53, 63),
(47, 54, 64),
(48, 55, 65),
(49, 33, 66),
(50, 33, 67);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `book_loan`
--

CREATE TABLE `book_loan` (
  `id` int(11) NOT NULL,
  `estimate_end_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  `book_id` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `last_mod_user` varchar(100) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `last_mod_date` datetime DEFAULT NULL,
  `extended_date` date DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_hungarian_ci;

--
-- A tábla adatainak kiíratása `book_loan`
--

INSERT INTO `book_loan` (`id`, `estimate_end_date`, `end_date`, `book_id`, `created_date`, `created_by`, `last_mod_user`, `last_mod_date`, `extended_date`, `user_id`) VALUES
(1, '2022-02-12', NULL, 18, '2021-12-12 00:00:00', 'norbi', 'nbalogh', '2022-01-24 19:20:21', '2022-02-19', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `last_mod_user` varchar(100) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `last_mod_date` datetime DEFAULT NULL,
  `img` varchar(255) COLLATE utf16_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_hungarian_ci;

--
-- A tábla adatainak kiíratása `category`
--

INSERT INTO `category` (`id`, `name`, `created_date`, `created_by`, `last_mod_user`, `last_mod_date`, `img`) VALUES
(1, 'Irodalom', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://cdn.nwmgroups.hu/s/img/i/2105/20210520irodalom.jpg'),
(2, 'Életmód, egészség', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://m.blog.hu/eo/eotvoskollegium/image/.external/.thumbs/6ca6c15dca5a51c560af1aadd013d8b4_d49afe6e3b4eb7cadfe308837303ec67.jpg'),
(3, 'Család', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, NULL),
(4, 'Film', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://www.bbc.co.uk/staticarchive/251f6cc7d40756cd2bf7916ab792feb7c6fc9bb2.jpg'),
(5, 'Utazás', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://online.suli.hu/uploaded/blog/online-tortenelem-tanar-segit.jpg'),
(6, 'Játék', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, NULL),
(7, 'Térkép', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, NULL),
(8, 'Élatrajz', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, NULL),
(9, 'Pénz, gazdaság, üzlet', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://www.opcioguru.com/images/cikkek/20180607_blogkepek/kamat.jpg'),
(10, 'Művészet, épsítészet', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, NULL),
(11, 'Hobbi, szabadidő', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, NULL),
(12, 'Gasztronómia', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://www.otptravel.hu/userfiles/Inspiraciok/Mexiko-gasztro-utikalauz/cover.jpg'),
(13, 'Tankönyvek, segédkönyvek', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://gremmedia.hu/storage/temp/public/0b2/94b/d61/thumb_111_800_800_0_0_auto__800.jpg'),
(14, 'Gyermek és ifjúsági', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, NULL),
(15, 'Történelem', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://nullahategy.hu/wp-content/uploads/sites/9/2020/04/history.jpg'),
(16, 'Vallás', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://honvedelem.hu/images/media/5f224333860aa025192217.jpg'),
(17, 'Kéregény', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, 'https://infoneked.hu/wp-content/uploads/2019/09/kepregeny.jpg'),
(18, 'Hangoskönyvek', '2021-12-20 15:11:17', 'nbalogh', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reminder`
--

CREATE TABLE `reminder` (
  `id` int(11) NOT NULL,
  `loan_item_id` int(11) NOT NULL,
  `notification_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_hungarian_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `title` varchar(10) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `firstname` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `middlename` varchar(100) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `lastname` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `email` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `username` varchar(15) COLLATE utf16_hungarian_ci NOT NULL,
  `encrypted_password` varchar(500) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `salt` varchar(500) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  `user_type_id` int(11) NOT NULL,
  `QR_code` varchar(100) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_by` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `last_mod_user` varchar(100) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `last_mod_date` datetime DEFAULT NULL,
  `deactivation_date` datetime DEFAULT NULL,
  `postcode` varchar(15) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `city` varchar(30) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `street` varchar(30) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `street_number` varchar(10) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `language` varchar(2) COLLATE utf16_hungarian_ci DEFAULT 'HU',
  `phone_number` varchar(15) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `user_img` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_hungarian_ci;

--
-- A tábla adatainak kiíratása `users`
--

INSERT INTO `users` (`id`, `title`, `firstname`, `middlename`, `lastname`, `email`, `username`, `encrypted_password`, `salt`, `active`, `user_type_id`, `QR_code`, `created_date`, `created_by`, `last_mod_user`, `last_mod_date`, `deactivation_date`, `postcode`, `city`, `street`, `street_number`, `birth_date`, `language`, `phone_number`, `user_img`) VALUES
(1, NULL, 'Norbert', NULL, 'Balogh', 'balogh.norbert92@gmail.com', 'nbalogh', '123456', NULL, 1, 3, NULL, '2021-12-12 00:00:00', 'nbalogh', 'anonymousUser', '2022-04-25 08:48:22', NULL, '6726', 'Szeged', 'Kocka', '42', '1992-08-17 00:00:00', 'HU', NULL, NULL),
(2, 'Mr', 'Elek', NULL, 'Teszt', 'invalid@gmail.com', 'tesztelek_', '123456', '', 1, 3, 'tesztelekeqr', '2022-01-07 14:11:38', 'nbalogh', 'nbalogh', '2022-01-07 14:11:38', NULL, '6726', 'Szeged', 'Tisza Lajos', '42', '2022-01-12 20:42:45', 'HU', '+36 70 1234567 ', NULL),
(3, 'title', 'Nándor', NULL, 'Tápai', 'tapai43@gmail.com', 'tnandi', '123456', NULL, 1, 2, NULL, '2022-01-07 14:12:56', 'nbalogh', 'nbalogh', '2022-01-07 14:12:56', NULL, '6726', 'Szeged', 'XY utca', '2', '2012-01-18 15:42:49', 'HU', '+36 789 7889', NULL),
(4, 'Mr', 'Pintér', NULL, 'Pista', 'pista.pinter@gmail.com', 'piPista', '123456', NULL, 1, 1, 'éööö1314ö79_', '2022-04-23 18:04:25', 'tnandi', NULL, NULL, NULL, '5000', 'Szolnok', 'Hunyadi János utca', '2', '1996-05-09 00:00:00', 'HU', '06305522341', NULL),
(5, '', 'Kapros', NULL, 'Júlia', 'kaporka@gmail.com', 'kaJulia', '123456', NULL, 1, 1, 'éöö15429491_', '2022-04-23 18:04:25', 'tnandi', NULL, NULL, NULL, '6721', 'Szeged', 'Római krt', '2', '1991-07-10 00:00:00', 'HU', '06708568463', NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user_type`
--

CREATE TABLE `user_type` (
  `id` int(11) NOT NULL,
  `name` varchar(15) COLLATE utf16_hungarian_ci NOT NULL,
  `created_date` datetime NOT NULL,
  `created_by` varchar(100) COLLATE utf16_hungarian_ci NOT NULL,
  `last_mod_user` varchar(100) COLLATE utf16_hungarian_ci DEFAULT NULL,
  `last_mod_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_hungarian_ci;

--
-- A tábla adatainak kiíratása `user_type`
--

INSERT INTO `user_type` (`id`, `name`, `created_date`, `created_by`, `last_mod_user`, `last_mod_date`) VALUES
(1, 'ADMIN', '2021-12-13 00:00:00', '', 'nbalogh', NULL),
(2, 'LIBRARIAN', '2021-12-13 00:00:00', '', 'nbalogh', NULL),
(3, 'USER', '2021-12-13 00:00:00', '', 'nbalogh', NULL);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- A tábla indexei `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `barcode` (`barcode`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `title` (`title`),
  ADD KEY `loanable` (`loanable`),
  ADD KEY `categoryindex` (`category_id`);

--
-- A tábla indexei `book_author`
--
ALTER TABLE `book_author`
  ADD PRIMARY KEY (`id`),
  ADD KEY `author_id` (`author_id`),
  ADD KEY `book_id` (`book_id`);

--
-- A tábla indexei `book_loan`
--
ALTER TABLE `book_loan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `user_id_fk` (`user_id`);

--
-- A tábla indexei `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- A tábla indexei `reminder`
--
ALTER TABLE `reminder`
  ADD PRIMARY KEY (`id`),
  ADD KEY `loan_item_id` (`loan_item_id`);

--
-- A tábla indexei `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `user_type_id` (`user_type_id`),
  ADD KEY `active` (`active`),
  ADD KEY `userQRIndex` (`QR_code`);

--
-- A tábla indexei `user_type`
--
ALTER TABLE `user_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `author`
--
ALTER TABLE `author`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=214;

--
-- AUTO_INCREMENT a táblához `book`
--
ALTER TABLE `book`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT a táblához `book_author`
--
ALTER TABLE `book_author`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT a táblához `book_loan`
--
ALTER TABLE `book_loan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT a táblához `reminder`
--
ALTER TABLE `reminder`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT a táblához `user_type`
--
ALTER TABLE `user_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Megkötések a táblához `book_author`
--
ALTER TABLE `book_author`
  ADD CONSTRAINT `book_author_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
  ADD CONSTRAINT `book_author_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`);

--
-- Megkötések a táblához `book_loan`
--
ALTER TABLE `book_loan`
  ADD CONSTRAINT `book_loan_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  ADD CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Megkötések a táblához `reminder`
--
ALTER TABLE `reminder`
  ADD CONSTRAINT `reminder_ibfk_1` FOREIGN KEY (`loan_item_id`) REFERENCES `book_loan` (`id`);

--
-- Megkötések a táblához `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
