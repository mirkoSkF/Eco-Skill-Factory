-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Creato il: Ago 16, 2026 alle 15:14
-- Versione del server: 5.7.24
-- Versione PHP: 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eco_skillfactory`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `block_items`
--

CREATE TABLE `block_items` (
  `id` bigint(20) NOT NULL,
  `button_text` varchar(255) DEFAULT NULL,
  `button_url` varchar(255) DEFAULT NULL,
  `content_html` longtext,
  `image_url` varchar(255) DEFAULT NULL,
  `item_order` int(11) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `page_block_id` bigint(20) DEFAULT NULL,
  `background_color` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `block_items`
--

INSERT INTO `block_items` (`id`, `button_text`, `button_url`, `content_html`, `image_url`, `item_order`, `subtitle`, `title`, `page_block_id`, `background_color`) VALUES
(1, 'Maggiori informazioni', '/p/java-full', '<div class=\"div-block-course-subtitle\">\r\n<p class=\"course-subtitle\" style=\"line-height: 1;\">Esperto di Java / Spring</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-51-copy\">Codice: &nbsp;</strong></p>\r\n<p class=\"coursecodice_black\">SFJFSD_02</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-52\">Ore: &nbsp;</strong></p>\r\n<p class=\"courseore_black\">240</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-53\">Modalit&agrave;: &nbsp;</strong></p>\r\n<p class=\"coursemodalita_black\">In presenza</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-54\">Start: &nbsp;</strong></p>\r\n<p class=\"coursestart_black black\">6/7/2026</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-54\">Iscrizioni: &nbsp;</strong></p>\r\n<p class=\"coursestart_black black\">Aperte</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-54\">Posti disponibili: &nbsp;</strong></p>\r\n<p class=\"course-posti-disponibili black\">15</p>\r\n</div>\r\n<div class=\"div-block-252\">\r\n<p class=\"paragraph-62\" style=\"line-height: 1;\">Il corso, finanziato da&nbsp;<strong>Forma.Temp</strong>, verr&agrave; erogato dal luned&igrave; al venerd&igrave;, dalle 9.30 alle 17.30 ed &egrave; destinato a giovani in cerca di occupazione.</p>\r\n</div>', '/uploads/7b8079cd-948a-41ac-bc80-400ba48f3d95_1.png', 0, NULL, 'Java Full Stack Developer - Laboratorio Estivo', 4, 'rgba(16,81,234,0.80)'),
(2, 'Maggiori informazioni', '/p/cloud-cyber', '<div class=\"div-block-course-subtitle\">\r\n<p class=\"course-subtitle\" style=\"line-height: 1;\">Esperto di Sistemi Operativi, Reti e Sicurezza</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-51-copy\">Codice: &nbsp;</strong></p>\r\n<p class=\"coursecodice_black\">SFSCC_01</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-52\">Ore: &nbsp;</strong></p>\r\n<p class=\"courseore_black\">160</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-53\">Modalit&agrave;: &nbsp;</strong></p>\r\n<p class=\"coursemodalita_black\">In presenza</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-54\">Start: &nbsp;</strong></p>\r\n<p class=\"coursestart_black black\">13/7/2026</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-54\">Iscrizioni: &nbsp;</strong></p>\r\n<p class=\"coursestart_black black\">Aperte</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-54\">Posti disponibili: &nbsp;</strong></p>\r\n<p class=\"course-posti-disponibili black\">15</p>\r\n</div>\r\n<div class=\"div-block-252\">\r\n<p class=\"paragraph-62\" style=\"line-height: 1;\">Il corso, finanziato da&nbsp;<strong>Forma.Temp</strong>, verr&agrave; erogato dal luned&igrave; al venerd&igrave;, dalle 9.30 alle 17.30 ed &egrave; destinato a giovani in cerca di occupazione.</p>\r\n</div>', '/uploads/10fb8206-3ed0-4849-8dc1-81dcbdb978fb_cl.png', 0, NULL, 'Sistemista Cloud e Cybersecurity - Laboratorio Estivo', 4, '#192748'),
(3, 'Maggiori informazioni', '/p/segr-coord', '<div class=\"div-block-course-subtitle\">\r\n<p class=\"course-subtitle\" style=\"line-height: 1;\">Esperto di Gestione e Amministrazione Aziendale</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-51-copy\">Codice: &nbsp;</strong></p>\r\n<p class=\"coursecodice_black\">SFOAC_01</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-52\">Ore: &nbsp;</strong></p>\r\n<p class=\"courseore_black\">160</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-53\">Modalit&agrave;: &nbsp;</strong></p>\r\n<p class=\"coursemodalita_black\">In presenza</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-54\">Start: &nbsp;</strong></p>\r\n<p class=\"coursestart_black black\">15/7/2026</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\"><strong class=\"bold-text-54\">Iscrizioni: &nbsp;</strong></p>\r\n<p class=\"coursestart_black black\">Aperte</p>\r\n</div>\r\n<div class=\"div-block-252\" style=\"line-height: 1;\">\r\n<p class=\"paragraph-62\" style=\"line-height: 1;\"><strong class=\"bold-text-54\">Posti disponibili: &nbsp;</strong></p>\r\n<p class=\"course-posti-disponibili black\">15</p>\r\n</div>\r\n<div class=\"div-block-252\">\r\n<p class=\"paragraph-62\" style=\"line-height: 1;\">Il corso, finanziato da&nbsp;<strong>Forma.Temp</strong>, verr&agrave; erogato dal luned&igrave; al venerd&igrave;, dalle 9.30 alle 17.30 ed &egrave; destinato a giovani in cerca di occupazione.</p>\r\n</div>', '/uploads/7c20fca4-c385-470a-91ee-145f5330d41b_seg.png', 0, NULL, 'Segretario Coordinatore Amministrativo - Laboratorio Estivo', 4, '');

-- --------------------------------------------------------

--
-- Struttura della tabella `course_form_configs`
--

CREATE TABLE `course_form_configs` (
  `id` bigint(20) NOT NULL,
  `course_code` varchar(255) DEFAULT NULL,
  `course_name` varchar(255) DEFAULT NULL,
  `course_type` varchar(255) DEFAULT NULL,
  `recipient_email` varchar(255) DEFAULT NULL,
  `page_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `course_form_configs`
--

INSERT INTO `course_form_configs` (`id`, `course_code`, `course_name`, `course_type`, `recipient_email`, `page_id`) VALUES
(1, 'prova', 'prova', 'prova', 'mirko.onorato@gmail.com', 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `footer_config`
--

CREATE TABLE `footer_config` (
  `id` bigint(20) NOT NULL,
  `background_color` varchar(255) DEFAULT NULL,
  `copyright_text` varchar(255) DEFAULT NULL,
  `opacity` double DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `facebook_url` varchar(255) DEFAULT NULL,
  `instagram_url` varchar(255) DEFAULT NULL,
  `linkedin_url` varchar(255) DEFAULT NULL,
  `vat_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `footer_config`
--

INSERT INTO `footer_config` (`id`, `background_color`, `copyright_text`, `opacity`, `address`, `facebook_url`, `instagram_url`, `linkedin_url`, `vat_number`) VALUES
(1, '#091430', '© 2026 Eco Skill Factory. Tutti i diritti riservati.', 0, 'Sede operativa: Via G. Porzio Isola E2 - 80143 Napoli NA ', 'https://www.facebook.com/SkillFactorySEAL', 'https://www.instagram.com/skillfactory_academy/', 'https://www.linkedin.com/company/skill-factory-srl/', 'Partita IVA n. 04956010658');

-- --------------------------------------------------------

--
-- Struttura della tabella `footer_items`
--

CREATE TABLE `footer_items` (
  `id` bigint(20) NOT NULL,
  `item_order` int(11) NOT NULL,
  `label` varchar(255) NOT NULL,
  `open_in_new_tab` bit(1) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `page_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `column_position` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `footer_items`
--

INSERT INTO `footer_items` (`id`, `item_order`, `label`, `open_in_new_tab`, `url`, `page_id`, `parent_id`, `column_position`) VALUES
(1, 0, 'Chi siamo', b'0', '', 2, NULL, 'SX'),
(2, 0, 'Eventi', b'0', '', 1, NULL, 'SX'),
(3, 0, 'Offerta', b'0', '', NULL, NULL, 'SX'),
(4, 0, 'Magazine', b'1', 'https://magazine.skillfactory.it', NULL, NULL, 'CENTRO'),
(5, 0, 'Training', b'1', 'https://training.skillfactory.it', NULL, NULL, 'CENTRO');

-- --------------------------------------------------------

--
-- Struttura della tabella `form_configs`
--

CREATE TABLE `form_configs` (
  `id` bigint(20) NOT NULL,
  `access_description` text,
  `access_title` varchar(255) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `course_code` varchar(255) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `course_type` varchar(255) NOT NULL,
  `page_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `form_configs`
--

INSERT INTO `form_configs` (`id`, `access_description`, `access_title`, `active`, `course_code`, `course_name`, `course_type`, `page_id`) VALUES
(1, 'Per partecipare puoi inviare il CV e una manifestazione di interesse all\'indirizzo selezione@skillfactory.it oppure compilare il form seguente:', 'Modalità di accesso', b'1', 'prova', 'prova', 'prova', 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `nav_items`
--

CREATE TABLE `nav_items` (
  `id` bigint(20) NOT NULL,
  `item_order` int(11) DEFAULT NULL,
  `label` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `page_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `open_in_new_tab` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `nav_items`
--

INSERT INTO `nav_items` (`id`, `item_order`, `label`, `url`, `page_id`, `parent_id`, `open_in_new_tab`) VALUES
(5, 3, 'Magazine', 'https://magazine.skillfactory.it', NULL, NULL, b'1'),
(6, 4, 'Training', 'https://training.skillfactory.it', NULL, NULL, b'1'),
(7, 0, 'Chi siamo', '', 2, NULL, b'0'),
(8, 1, 'Offerta', '', NULL, NULL, b'0'),
(9, 2, 'Eventi', '', 1, NULL, b'0');

-- --------------------------------------------------------

--
-- Struttura della tabella `pages`
--

CREATE TABLE `pages` (
  `id` bigint(20) NOT NULL,
  `content_html` longtext,
  `slug` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `width_percent` int(11) NOT NULL,
  `course_code` varchar(255) DEFAULT NULL,
  `course_name` varchar(255) DEFAULT NULL,
  `course_type` varchar(255) DEFAULT NULL,
  `has_form` bit(1) NOT NULL,
  `recipient_email` varchar(255) DEFAULT NULL,
  `content` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `pages`
--

INSERT INTO `pages` (`id`, `content_html`, `slug`, `title`, `width_percent`, `course_code`, `course_name`, `course_type`, `has_form`, `recipient_email`, `content`) VALUES
(1, '<div style=\"padding: 2%; background-color: #003153; width: 100%; margin: 0%; border-radius: 0.5%;\">\r\n<div class=\"w-layout-blockcontainer container-8 w-container\">\r\n<div class=\"div-block-235\">\r\n<h4 class=\"course-description-title\"><span style=\"color: #f1c40f;\">Meeting - 18/09/2026 - Sviluppo Software e IA</span></h4>\r\n<p class=\"paragraph-55 course-description-text\"><span style=\"font-size: 24px;\"><em>Intelligenza artificiale | Cosa cambia nel ciclo di vita dello sviluppo software | Le nuove figure professionali e le loro competenze</em></span></p>\r\n<p class=\"paragraph-55 course-description-text\">&nbsp;</p>\r\n</div>\r\n</div>\r\n<p><span style=\"font-size: 24px;\"><img src=\"https://cdn.prod.website-files.com/637e46ff6c683476b4d8faf5/6a60c62ab43ce2eea93c8f27_b6263ff04a92b238b48d6942f7763355_evento1.png\" width=\"832\" height=\"410\"></span></p>\r\n<p>&nbsp;</p>\r\n<p><span style=\"font-size: 24px;\">A settembre gli esperti di <strong>sviluppo software</strong>&nbsp;si incon&shy;trano a&nbsp;<strong>Napoli&nbsp;</strong>per tracciare il futuro del settore.</span><br><span style=\"font-size: 24px;\">La rivol&shy;uzione dell\'<strong>Intell&shy;igenza Artif&shy;icial</strong>e sta ridise&shy;gnando da zero i processi con cui conce&shy;piamo, scriviamo e validiamo il software. Di fronte a questo cambio di&nbsp;<strong>paradigma</strong>, la domanda non &egrave; pi&ugrave; se le cose cambi&shy;eranno, ma come governare questa trans&shy;izione.</span><br><span style=\"font-size: 24px;\">Per rispo&shy;ndere a questa sfida, il prossimo <strong>18 settembre</strong>,&nbsp;<strong>Skill Factory</strong>&nbsp;ospiter&agrave; a&nbsp;<strong>Napoli</strong>&nbsp;i maggiori esperti di sviluppo software per mappare l\'impatto dell\'<strong>IA</strong> sul settore e definire quali saranno gli scenari futuri.</span></p>\r\n</div>', 'eventi', 'Eventi', 90, NULL, NULL, NULL, b'0', NULL, NULL),
(2, '<h2 style=\"text-align: justify;\"><span style=\"font-size: 36px;\"><img src=\"/uploads/2d892055-6809-4894-8101-bb81981566d4.jpg\" alt=\"\" width=\"806\" height=\"453\"></span></h2>\r\n<p>&nbsp;</p>\r\n<p><span style=\"font-size: 24px;\"><span style=\"color: #f1c40f;\"><strong>Skill Factory</strong></span> &egrave; la <strong>Learning Company</strong> specializzata in <strong>servizi e prodotti per la formazione</strong>. </span></p>\r\n<p><span style=\"font-size: 24px;\">Nasce nel&nbsp;<strong>2011</strong>, con l\'obiettivo di <strong>valorizzare</strong> le risorse umane attraverso la riduzione dello <strong>skill shortage</strong>,&nbsp;</span><span style=\"font-size: 24px;\">la carenza di competenze che si crea nel&nbsp;<strong>mercato del lavoro</strong> a causa dei continui cambiamenti dovuti alla <strong>trasformazione digitale</strong>.</span></p>\r\n<p><span style=\"font-size: 24px;\">La nostra <strong>Mission</strong> &egrave; quella di ridurre il <strong>mismatch</strong> tra <strong>domanda e offerta</strong> di lavoro. Progettiamo ed eroghiamo <strong>corsi di formazione</strong> per creare le <strong>figure professionali</strong> pi&ugrave; richieste dalle aziende, individuate attraverso l\'<strong>analisi continua dei fabbisogni formativi</strong> del <strong>mercato del lavoro</strong>. Ogni anno eroghiamo oltre <strong>5000 ore di formazione</strong> e <strong>7000 ore di laboratorio</strong>, sia in modalit&agrave; <strong>sincrona</strong>, sia <strong>asincrona</strong>.</span></p>', 'chi-siamo', 'Chi siamo', 90, NULL, NULL, NULL, b'0', NULL, NULL),
(3, '<h2 class=\"course-subtitle-template\">Esperto di Java / Spring</h2>\r\n<p>&nbsp;</p>\r\n<p><img src=\"https://cdn.prod.website-files.com/65cce75075d5ba7c6d788bf5/6a057f0d5652e6615a9d2221_FullStackDeveloper.png\" width=\"842\" height=\"459\"></p>\r\n<p>&nbsp;</p>\r\n<div class=\"div-block-256\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">Codice:</span></p>\r\n<p class=\"coursecodice\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">SFJFSD_02</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Ore:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">240</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Modalit&agrave;:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">In presenza</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Start:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">6/7/2026</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Costo:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Gratuito</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Orario settimanale:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Dal luned&igrave; al venerd&igrave; - dalle ore 9:30 alle ore 17:30</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Stato Iscrizioni - Candidature:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Aperte</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">Posti disponibili:</span></p>\r\n<div class=\"div-block-260\">\r\n<p class=\"coursepostidisponibili\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">15</span></p>\r\n<p class=\"coursepostidisponibili\">&nbsp;</p>\r\n</div>\r\n</div>\r\n</div>\r\n<div>\r\n<h2 class=\"heading-31\">Vuoi diventare Java Full Stack Developer?</h2>\r\n<div class=\"rich-text-block-4 w-richtext\">\r\n<p><span style=\"font-size: 24px;\">Partecipa al corso&nbsp;<strong>Java&nbsp;Full Stack Developer</strong>&nbsp;e impara a svolgere il ruolo di&nbsp;<strong>Programmatore</strong>&nbsp;o&nbsp;<strong>Tester</strong>&nbsp;in ambiente&nbsp;<strong>DEVOPS</strong>.</span></p>\r\n<p><span style=\"font-size: 24px;\">A fine corso avrai acquisito le seguenti competenze: &zwj;</span></p>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Linux/MSDOS,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Reti, Pila ISO/OSI,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Logica di programmazione di base,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Algoritmi notevoli,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Linguaggio SQL,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Object Oriented con UML,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Linguaggio Java,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">GIT/MAVEN,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">HTML/CSS,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">JavaScript,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Spring,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Microservizi,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Docker,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">il ciclo di sviluppo DevOps,</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Processo CI/CD.</span></li>\r\n</ul>\r\n</div>\r\n</div>', 'java-full', 'Java Full Stack Developer - Laboratorio Estivo', 90, 'SFJFSD_02', 'Java Full Stack Developer - Laboratorio Estivo', 'Formazione professionale', b'1', 'mirko.onorato@gmail.com', NULL),
(4, '<h2 class=\"course-subtitle-template\">Esperto di Sistemi Operativi, Reti e Sicurezza</h2>\r\n<p>&nbsp;</p>\r\n<p><img src=\"https://cdn.prod.website-files.com/65cce75075d5ba7c6d788bf5/69e88c870f2b9c4f85bd9b7d_reti_sicurezza.png\" width=\"827\" height=\"451\"></p>\r\n<p>&nbsp;</p>\r\n<div class=\"div-block-256\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">Codice:</span></p>\r\n<p class=\"coursecodice\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">SFSCC_01</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Ore:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">160</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Modalit&agrave;:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">In presenza</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Start:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">13/7/2026</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Costo:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Gratuito</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Orario settimanale:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Dal luned&igrave; al venerd&igrave; - dalle ore 9:30 alle ore 17:30</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Stato Iscrizioni - Candidature:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Aperte</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">Posti disponibili:</span></p>\r\n<div class=\"div-block-260\">\r\n<p class=\"coursepostidisponibili\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">15</span></p>\r\n<p class=\"coursepostidisponibili\">&nbsp;</p>\r\n</div>\r\n</div>\r\n</div>\r\n<div>\r\n<h2 class=\"heading-31\">Vuoi diventare un Sistemista Cloud e un tecnico di Cybersecurity?</h2>\r\n<div class=\"rich-text-block-4 w-richtext\">\r\n<p><span style=\"font-size: 24px;\">Il Sistemista Cloud e il tecnico di Cybersecurity trasforma la passione per l\'informatica in una carriera solida, partendo dalle basi per arrivare alle competenze pi&ugrave; avanzate.</span></p>\r\n<p><span style=\"font-size: 24px;\">I partecipanti impareranno l\'architettura dei computer, i concetti fondamentali delle reti e della sicurezza informatica.</span></p>\r\n<p><span style=\"font-size: 24px;\">Il percorso formativo prevede l\'acquisizione delle conoscenze e delle abilit&agrave; per lavorare e configurare i seguenti sistemi operativi:</span></p>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">MS-DOS</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Windows (PowerShell)</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Linux</span></li>\r\n</ul>\r\n<p><span style=\"font-size: 24px;\">Inoltre i partecipanti impareranno:</span></p>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">a scrivere script per l\'automazione dei processi di sistema come ad esempio:</span><br>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">backup automatici e recupero dati</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">gestione dei container &nbsp;</span></li>\r\n</ul>\r\n</li>\r\n</ul>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">proteggere l\'infrastruttura informatica, applicando le regole fondamentali della sicurezza per difendere i dati da accessi non autorizzati, virus e attacchi informatici.</span></li>\r\n</ul>\r\n<p><span style=\"font-size: 24px;\">Particolare attenzione verr&agrave; rivolta al cloud computing per imparare a gestire macchine virtuali, spazi di archiviazione e servizi per le aziende.</span></p>\r\n<p><span style=\"font-size: 24px;\">Tutte le attivit&agrave; verranno supportate da tecniche di prompt engineering per sfruttare al meglio la potenza e le capacit&agrave; dell\'intelligenza artificiale.</span><br><span style=\"font-size: 24px;\">&zwj;</span></p>\r\n<h4><span style=\"font-size: 24px;\">Articolazione Didattica</span></h4>\r\n<p><span style=\"font-size: 24px;\"><strong>Modulo 1</strong>: Fondamentai Hardware e Software</span></p>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Architettura dei computer: CPU, RAM, Storage e BUS.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">MS-DOS &amp; Command Line Basics: Sintassi, gestione file system e creazione di file .bat (Batch).</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Logica dei Sistemi Operativi: Kernel, processi e gestione della memoria.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Prompt Engineering per l\'apprendimento: Usare l\'AI per spiegare concetti tecnici complessi e debuggare i primi script.</span></li>\r\n</ul>\r\n<p><span style=\"font-size: 24px;\"><strong>Modulo 2</strong>: Windows &amp; PowerShell</span></p>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Amministrazione avanzata di Windows: Registry, servizi e gestione utenti.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">PowerShell Scripting: Variabili, cicli, oggetti e pipeline.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Automazione Professionale: Script per il monitoraggio delle risorse e gestione degli aggiornamenti.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Prompt Engineering: Generazione di script PowerShell complessi tramite AI e validazione del codice.</span></li>\r\n</ul>\r\n<p><span style=\"font-size: 24px;\"><strong>Modulo 3</strong>: Linux &amp; Amministrazione di Sistema</span></p>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Fondamenti Linux: Installazione, Bash scripting e gestione dei permessi (chmod/chown).</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Gestione pacchetti e repository: Debian/Ubuntu vs RedHat/CentOS.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Automazione dei processi: Utilizzo di cron per task pianificati e backup automatici.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Recupero Dati: Tecniche di disaster recovery e gestione dei log di sistema.</span></li>\r\n</ul>\r\n<p><span style=\"font-size: 24px;\"><strong>Modulo 4</strong>: Reti e Sicurezza Informatica</span></p>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Networking Essentials: Modello ISO/OSI, TCP/IP, DNS, DHCP e routing.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Cybersecurity Fundamentals: Tipologie di attacchi (Phishing, Ransomware, Brute Force).</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Hardening del sistema: Configurazione firewall, VPN e protocolli crittografici (SSH, SSL/TLS).</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Security Auditing con AI: Usare i LLM per analizzare vulnerabilit&agrave; nel codice e nei log di rete.</span></li>\r\n</ul>\r\n<p><span style=\"font-size: 24px;\"><strong>Modulo 5</strong>: Virtualizzazione, Container e Cloud Computing</span></p>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Virtualizzazione: Gestione di Hyper-V e VirtualBox.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Docker &amp; Containerizzazione: Creazione di immagini, gestione di volumi e orchestrazione base.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Cloud Computing: Introduzione a AWS/Azure/Google Cloud (VM, Storage S3, Networking Cloud).</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\">Backup nel Cloud: Strategie di archiviazione remota e ridondanza dei dati.</span></li>\r\n</ul>\r\n<p><span style=\"font-size: 24px;\"><strong>Modulo 6</strong>: Project Work &amp; AI Integration</span><br><span style=\"font-size: 24px;\">Mettere in pratica tutto il percorso attraverso un progetto reale.</span></p>\r\n<p><span style=\"font-size: 24px;\">&zwj;</span></p>\r\n</div>\r\n</div>', 'cloud-cyber', 'Sistemista Cloud e Cybersecurity - Laboratorio Estivo', 90, 'SFSCC_01', 'Sistemista Cloud e Cybersecurity - Laboratorio Estivo', 'Formazione professionale', b'1', 'mirko.onorato@gmail.com', NULL),
(5, '<h2 class=\"course-subtitle-template\">Esperto di Gestione e Amministrazione Aziendale</h2>\r\n<p>&nbsp;</p>\r\n<p><img src=\"https://cdn.prod.website-files.com/65cce75075d5ba7c6d788bf5/69de207d27805b67c30b5775_operatore_contabile.png\" width=\"807\" height=\"454\"></p>\r\n<p>&nbsp;</p>\r\n<div class=\"div-block-256\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">Codice:</span></p>\r\n<p class=\"coursecodice\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">SFOAC_01</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Ore:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">160</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Modalit&agrave;:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">In presenza</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Start:</span></p>\r\n<p class=\"coursecodice\"><span style=\"font-size: 24px;\">15/7/2026</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Costo:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Gratuito</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Orario settimanale:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Dal luned&igrave; al venerd&igrave; - dalle ore 9:30 alle ore 17:30</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\" style=\"line-height: 1;\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\"><span style=\"font-size: 24px;\">Stato Iscrizioni - Candidature:</span></p>\r\n<p class=\"coursecosto\"><span style=\"font-size: 24px;\">Aperte</span></p>\r\n</div>\r\n</div>\r\n<div class=\"div-block-256\">\r\n<div class=\"div-block-257\">\r\n<p class=\"courselabel\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">Posti disponibili:</span></p>\r\n<div class=\"div-block-260\">\r\n<p class=\"coursepostidisponibili\" style=\"line-height: 1;\"><span style=\"font-size: 24px;\">15</span></p>\r\n<p class=\"coursepostidisponibili\">&nbsp;</p>\r\n</div>\r\n</div>\r\n</div>\r\n<div>\r\n<h2 class=\"heading-31\">Vuoi diventare un Segretario Coordinatore Amministrativo?</h2>\r\n<div class=\"rich-text-block-4 w-richtext\">\r\n<p><span style=\"font-size: 24px;\"><strong>Integra le competenze contabili tradizionali con l\'efficienza dell\'Office Automation e della gestione digitale.</strong></span></p>\r\n<p><span style=\"font-size: 24px;\">Partecipa al corso per&nbsp;<strong>Segretario Coordinatore Amministrativo</strong>&nbsp;e acquisisci il metodo di lavoro necessario per gestire i flussi gestionali delle aziende moderne e della Pubblica Amministrazione. In questo percorso non imparerai solo a gestire la contabilit&agrave;, ma svilupperai un approccio operativo basato sull&rsquo;<strong>efficienza digitale</strong>, utilizzando strumenti avanzati di&nbsp;<strong>Office Automation</strong>&nbsp;e tecniche di gestione documentale informatizzata per ottimizzare ogni processo d\'ufficio.</span></p>\r\n<p><span style=\"font-size: 24px;\">Per rispondere alla crescente domanda dei nostri&nbsp;<strong>Partner Aziendali</strong>&nbsp;di figure amministrative versatili, capaci di coniugare rigore contabile e agilit&agrave; digitale, abbiamo progettato un iter formativo che integra le basi dell&rsquo;economia aziendale con le tecnologie pi&ugrave; attuali per la produttivit&agrave;.</span></p>\r\n<h3><span style=\"font-size: 24px;\">L\'articolazione didattica include:</span></h3>\r\n<ul role=\"list\">\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\"><strong>Sistema Azienda e Organizzazione:</strong>&nbsp;Analisi dei processi interni, gestione dei flussi amministrativi e comprensione degli assetti organizzativi per operare con consapevolezza in ogni contesto strutturato.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\"><strong>Contabilit&agrave;:</strong>&nbsp;Gestione completa delle scritture contabili, dalla prima nota al bilancio, includendo gli adempimenti fiscali e la gestione dei rapporti con banche e fornitori.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\"><strong>Segreteria Efficiente:</strong>&nbsp;Tecniche di comunicazione professionale e pubbliche relazioni. Imparerai a gestire la corrispondenza, l\'archiviazione digitale e il front-office con un approccio orientato al problem solving.</span></li>\r\n<li style=\"font-size: 24px;\"><span style=\"font-size: 24px;\"><strong>Competenze Digitali:</strong>&nbsp;Padronanza assoluta della suite Office (Excel avanzato per la contabilit&agrave;, Word per la documentazione) e dei principali software gestionali.</span></li>\r\n</ul>\r\n<h3><span style=\"font-size: 24px;\">Supervisione e Qualit&agrave;</span></h3>\r\n<p><span style=\"font-size: 24px;\">Il percorso pone un accento particolare sulla&nbsp;<strong>validazione e revisione del dato</strong>: imparerai a supervisionare i processi automatizzati per garantire l\'assenza di errori, la sicurezza dei dati sensibili e la conformit&agrave; alle normative vigenti.</span></p>\r\n<p><span style=\"font-size: 24px;\"><strong>Il risultato finale:</strong>Al termine del percorso, i partecipanti saranno in grado di gestire l\'intero ciclo amministrativo di un\'impresa o di un ente pubblico, assicurando una produttivit&agrave; superiore agli standard grazie all\'uso sapiente degli strumenti digitali e a una visione d\'insieme del sistema azienda.</span></p>\r\n<p><span style=\"font-size: 24px;\"><strong>Il tuo futuro lavorativo inizia oggi: diventa il fulcro operativo dell\'azienda di domani.</strong></span></p>\r\n</div>\r\n</div>', 'segr-coord', 'Segretario Coordinatore Amministrativo - Laboratorio Estivo', 90, 'SFOAC_01', 'Segretario Coordinatore Amministrativo - Laboratorio Estivo', 'Formazione professionale', b'1', 'mirko.onorato@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `page_blocks`
--

CREATE TABLE `page_blocks` (
  `id` bigint(20) NOT NULL,
  `background_color` varchar(255) DEFAULT NULL,
  `block_type` enum('JUMBOTRON','CARDS','CAROUSEL','JUMBO_DEMO_1','JUMBO_DEMO_2','JUMBO_2_COL') NOT NULL,
  `container_type` enum('CONTAINER','CONTAINER_FLUID') NOT NULL,
  `content_html` longtext,
  `custom_height` varchar(50) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `title_admin` varchar(255) NOT NULL,
  `width_percent` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `page_blocks`
--

INSERT INTO `page_blocks` (`id`, `background_color`, `block_type`, `container_type`, `content_html`, `custom_height`, `image_url`, `position`, `title_admin`, `width_percent`) VALUES
(2, 'bg-white', 'JUMBOTRON', 'CONTAINER', '<div class=\"w-layout-blockcontainer container-8 w-container\">\r\n<div class=\"div-block-235\">\r\n<h1 class=\"title-events\">Prossimi Eventi</h1>\r\n<p>&nbsp;</p>\r\n<h4 class=\"course-description-title\">Meeting - 18/09/2026 - Sviluppo Software e IA</h4>\r\n<p class=\"paragraph-55 course-description-text\"><span style=\"font-size: 24px;\"><em>Intelligenza artificiale | Cosa cambia nel ciclo di vita dello sviluppo software | Le nuove figure professionali e le loro competenze</em></span></p>\r\n<p class=\"paragraph-55 course-description-text\">&nbsp;</p>\r\n</div>\r\n</div>\r\n<p><span style=\"font-size: 24px;\"><img src=\"https://cdn.prod.website-files.com/637e46ff6c683476b4d8faf5/6a60c62ab43ce2eea93c8f27_b6263ff04a92b238b48d6942f7763355_evento1.png\" width=\"832\" height=\"410\"></span></p>\r\n<p>&nbsp;</p>\r\n<p><span style=\"font-size: 24px;\">A settembre gli esperti di <strong>sviluppo software</strong>&nbsp;si incon&shy;trano a&nbsp;<strong>Napoli&nbsp;</strong>per tracciare il futuro del settore.</span><br><span style=\"font-size: 24px;\">La rivol&shy;uzione dell\'<strong>Intell&shy;igenza Artif&shy;icial</strong>e sta ridise&shy;gnando da zero i processi con cui conce&shy;piamo, scriviamo e validiamo il software. Di fronte a questo cambio di&nbsp;<strong>paradigma</strong>, la domanda non &egrave; pi&ugrave; se le cose cambi&shy;eranno, ma come governare questa trans&shy;izione.</span><br><span style=\"font-size: 24px;\">Per rispo&shy;ndere a questa sfida, il prossimo <strong>18 settembre</strong>,&nbsp;<strong>Skill Factory</strong>&nbsp;ospiter&agrave; a&nbsp;<strong>Napoli</strong>&nbsp;i maggiori esperti di sviluppo software per mappare l\'impatto dell\'<strong>IA</strong> sul settore e definire quali saranno gli scenari futuri.</span></p>', 'auto', NULL, 1, 'Prossimi Eventi', 90),
(3, 'rgba(12,80,237,0.13)', 'JUMBOTRON', 'CONTAINER', '<h3 class=\"sub-claim\"><span style=\"font-family: \'arial black\', sans-serif; color: #ffffff;\">L\'academy delle professioni digitali</span></h3>\r\n<h1 class=\"post-description-title desktop\"><span style=\"color: #3598db; font-size: 100px; font-family: \'arial black\', sans-serif;\">Benvenuto </span></h1>\r\n<h1 class=\"post-description-title desktop\"><span style=\"color: #3598db; font-size: 100px; font-family: \'arial black\', sans-serif;\">in Skill Factory.</span></h1>\r\n<h1 class=\"subhead home\" style=\"line-height: 1;\"><span style=\"font-size: 28px;\">Offriamo esperienze di formazione&nbsp;</span><span style=\"font-size: 28px;\">professionale </span></h1>\r\n<h1 class=\"subhead home\" style=\"line-height: 1;\"><span style=\"font-size: 28px;\">per entrare nel mondo del lavoro e restare sempre </span></h1>\r\n<h1 class=\"subhead home\" style=\"line-height: 1;\"><span style=\"font-size: 28px;\">aggiornati e competitivi.</span></h1>', 'auto', NULL, 0, 'Benvenuto', 100),
(4, 'rgba(11,17,30,1.00)', 'CARDS', 'CONTAINER', '', 'auto', NULL, 2, 'Cards1', 90),
(6, 'rgba(0,0,0,1.00)', 'JUMBOTRON', 'CONTAINER', '<h1 class=\"post-description-title white\"><span style=\"font-size: 60px;\">Perch&egrave; scegliere</span><br><span style=\"font-size: 60px;\">Skill Factory</span></h1>', 'auto', NULL, 3, 'Perchè scegliere Skill Factory', 100),
(7, 'rgba(0,0,0,1.00)', 'JUMBO_DEMO_2', 'CONTAINER', '<h4 class=\"heading-3\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 28px;\">Vivi l\'azienda con noi</span></h4>\r\n<p class=\"text-s _14px\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 24px;\"><strong class=\"bold-text-2\">Lavoriamo sulla qualit&agrave; della formazione, non sulla quantit&agrave; degli studenti.<br>&zwj;</strong>Non solo lezioni, ma una vera e propria simulazione di vita aziendale. Formarsi in presenza significa respirare la cultura del lavoro, affinare le soft skill attraverso il contatto umano e costruire relazioni professionali solide. Insieme al nostro Team di esperti, affronterai sessioni pratiche in un ambiente reale, dove lo scambio immediato di idee diventa il motore della tua crescita professionale.</span></p>', 'auto', '/uploads/d2111aae-0b0a-4735-9977-c1e20d70b622_ra.png', 4, 'Vivi l\'azienda con noi', 90),
(8, 'rgba(0,0,0,1.00)', 'JUMBO_DEMO_1', 'CONTAINER', '<h4 class=\"heading-3\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 28px;\">Hard Skills</span></h4>\r\n<p class=\"text-s _14px\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 24px;\"><strong class=\"bold-text-2\">Ti diamo tutte le competenze tecniche necessarie ad avviare la tua carriera digitale:&nbsp;</strong>programmazione, digital product design, pianificazione, project management, data engineering, client management&hellip; Scegli il tuo percorso e inizia subito a padroneggiare gli strumenti alla base della tua crescita professionale.</span></p>', 'auto', '/uploads/9f39db27-8236-4e3a-a2c4-765b125c86d1_n1.jpg', 5, 'Hard Skills', 90),
(9, 'rgba(0,0,0,1.00)', 'JUMBO_DEMO_2', 'CONTAINER', '<h4 class=\"heading-3\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 28px;\">Soft Skills</span></h4>\r\n<p class=\"text-s _14px\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 24px;\"><strong class=\"bold-text-2\">A prescindere dalla tua specializzazione tecnica e dal tuo ruolo, le soft skills fanno di te un professionista completo</strong> e ti permettono di portare valore aggiunto al tuo team, alla tua azienda e ai tuoi clienti. Dal problem-solving alla creativit&agrave;, dal decision making alla gestione del tempo, dalla comunicazione al lavoro di squadra: ti aiutiamo a sviluppare le competenze trasversali pi&ugrave; richieste dal mercato digitale e pi&ugrave; utili al tuo successo professionale.</span></p>', 'auto', '/uploads/f00d4ff1-8621-4a07-9099-3c14e9807019_n2.jpg', 6, 'Soft Skills', 90),
(10, 'rgba(0,0,0,1.00)', 'JUMBO_DEMO_1', 'CONTAINER', '<h4 class=\"heading-3\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 28px;\">Accessibilit&agrave; e formazione agile</span></h4>\r\n<p class=\"text-s _14px\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 24px;\"><strong class=\"bold-text-2\">I nostri corsi sono disponibili tutto l&rsquo;anno e puoi iniziare quando lo desideri.&nbsp;</strong>Scegli il corso che pi&ugrave; ti interessa e inizia subito a frequentare le lezioni. Grazie a un perfetto mix di teoria e pratica, prenderai parte ad un percorso di formazione che in pochi mesi porter&agrave; la tua expertise ad un livello superiore.</span></p>', 'auto', '/uploads/31c5ae7e-b41e-4328-b2b6-15bcf5909ceb_n3.jpg', 7, 'Accessibilità e formazione agile', 90),
(11, 'rgba(0,0,0,1.00)', 'JUMBO_DEMO_2', 'CONTAINER', '<h4 class=\"heading-3\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 28px;\">L&rsquo;Academy pensata per te</span></h4>\r\n<p class=\"text-s _14px\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 24px;\"><strong class=\"bold-text-2\">Prestiamo assoluta attenzione alla valorizzazione dei talenti e delle capacit&agrave;, anche potenziali, dei nostri studenti.<br>&zwj;</strong>La nostra offerta formativa punta alla qualit&agrave; dei contenuti e alla certificazione delle competenze necessarie per diventare un professionista del mondo digital. Ottimizza il tuo tempo al massimo, arricchisci le tue competenze e prendi parte ad un percorso strutturato e professionalizzante.</span></p>', 'auto', '/uploads/34a729c7-8a55-4dbe-b5e7-6e238e0c3fdc_n4.jpg', 8, 'L’Academy pensata per te', 90),
(12, 'rgba(0,0,0,1.00)', 'JUMBO_DEMO_1', 'CONTAINER', '<h4 class=\"heading-3\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 28px;\">Ti diamo quello che cerchi</span></h4>\r\n<p class=\"text-s _14px\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 24px;\"><strong class=\"bold-text-2\">La richiesta di professionisti, soprattutto con competenze digitali, &egrave; in costante crescita.<br>&zwj;</strong>Con il nostro modello, puoi affermare la tua autonomia professionale rispondendo alla crescente domanda di competenze tech delle aziende pi&ugrave; innovative del territorio italiano.</span></p>', 'auto', '/uploads/7209122a-e6fa-4c3b-96d1-a777e0955241_n5.jpg', 9, 'Ti diamo quello che cerchi', 90),
(13, 'rgba(0,0,0,1.00)', 'JUMBO_DEMO_2', 'CONTAINER', '<h4 class=\"heading-3\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 28px;\">Simulazione d&rsquo;impresa</span></h4>\r\n<p class=\"text-s _14px\" style=\"padding-left: 10%; padding-right: 10%;\"><span style=\"font-size: 24px;\"><strong class=\"bold-text-2\">Misurati con la realt&agrave; del lavoro in un ambiente dinamico e innovativo:&nbsp;</strong>metti subito in pratica tutto ci&ograve; che hai imparato su progetti simili a quelli di una big tech.</span></p>', 'auto', '/uploads/74ab0189-1405-4953-a39c-7331338fd474_n6.jpg', 10, 'Simulazione d’impresa', 90);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `block_items`
--
ALTER TABLE `block_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt0k37sdsbkrm5thmkdwab5knt` (`page_block_id`);

--
-- Indici per le tabelle `course_form_configs`
--
ALTER TABLE `course_form_configs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_jo3m18klictv1kaccobyhy34t` (`page_id`);

--
-- Indici per le tabelle `footer_config`
--
ALTER TABLE `footer_config`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `footer_items`
--
ALTER TABLE `footer_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo824gsigidwdqfxmr9qwk6oit` (`page_id`),
  ADD KEY `FKbcooxxopthwwk4sw4thrkv34e` (`parent_id`);

--
-- Indici per le tabelle `form_configs`
--
ALTER TABLE `form_configs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_1o28xw8i0jdhgb0kmyppylpcq` (`page_id`);

--
-- Indici per le tabelle `nav_items`
--
ALTER TABLE `nav_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg4l4kwio0ui7r6uhcpt237cdk` (`page_id`),
  ADD KEY `FK2s53xtj3775xuoi4dtcxjj2bi` (`parent_id`);

--
-- Indici per le tabelle `pages`
--
ALTER TABLE `pages`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_i2togwxhf7vhxn5pstqyu8jc8` (`slug`);

--
-- Indici per le tabelle `page_blocks`
--
ALTER TABLE `page_blocks`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `block_items`
--
ALTER TABLE `block_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `course_form_configs`
--
ALTER TABLE `course_form_configs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `footer_config`
--
ALTER TABLE `footer_config`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `footer_items`
--
ALTER TABLE `footer_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la tabella `form_configs`
--
ALTER TABLE `form_configs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `nav_items`
--
ALTER TABLE `nav_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `pages`
--
ALTER TABLE `pages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT per la tabella `page_blocks`
--
ALTER TABLE `page_blocks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `block_items`
--
ALTER TABLE `block_items`
  ADD CONSTRAINT `FKt0k37sdsbkrm5thmkdwab5knt` FOREIGN KEY (`page_block_id`) REFERENCES `page_blocks` (`id`);

--
-- Limiti per la tabella `course_form_configs`
--
ALTER TABLE `course_form_configs`
  ADD CONSTRAINT `FKlkfhbcemg7leryyixdr9u14fx` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`);

--
-- Limiti per la tabella `footer_items`
--
ALTER TABLE `footer_items`
  ADD CONSTRAINT `FKbcooxxopthwwk4sw4thrkv34e` FOREIGN KEY (`parent_id`) REFERENCES `footer_items` (`id`),
  ADD CONSTRAINT `FKo824gsigidwdqfxmr9qwk6oit` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`);

--
-- Limiti per la tabella `form_configs`
--
ALTER TABLE `form_configs`
  ADD CONSTRAINT `FKta43ryf9hihcpor6w1883cmhp` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`);

--
-- Limiti per la tabella `nav_items`
--
ALTER TABLE `nav_items`
  ADD CONSTRAINT `FK2s53xtj3775xuoi4dtcxjj2bi` FOREIGN KEY (`parent_id`) REFERENCES `nav_items` (`id`),
  ADD CONSTRAINT `FKg4l4kwio0ui7r6uhcpt237cdk` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
