import {
    archiveOutline,
    archiveSharp,
    bookOutline,
    bookSharp,
    mailOutline,
    mailSharp,
    peopleOutline,
    peopleSharp,
    searchOutline,
    searchSharp
} from "ionicons/icons";

interface AppPage {
    url: string;
    iosIcon: string;
    mdIcon: string;
    title: string;
}

export const appPages: AppPage[] = [
    {
        title: 'Profil',
        url: '/profile',
        iosIcon: peopleOutline,
        mdIcon: peopleSharp
    },
    {
        title: 'Könyv keresés',
        url: '/books',
        iosIcon: searchOutline,
        mdIcon: searchSharp
    },
    {
        title: 'Aktuális kölcsönzések',
        url: '/loans',
        iosIcon: bookOutline,
        mdIcon: bookSharp
    },
    {
        title: 'Korábbi kölcsönzések',
        url: '/history',
        iosIcon: archiveOutline,
        mdIcon: archiveSharp
    },
    {
        title: 'Kapcsolat',
        url: '/contact',
        iosIcon: mailOutline,
        mdIcon: mailSharp
    }
];