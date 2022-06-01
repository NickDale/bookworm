import {IonCardContent, IonCol, IonIcon, IonRow} from '@ionic/react';
import React from "react";

interface CellProps {
    name: string;
}

const ContactCellComponent: React.FC<CellProps> = (props) => {
    return (
        <IonCol size="3">
            <IonRow className="profileCard">
                <IonCardContent>
                    <IonIcon icon={props.name}/>
                </IonCardContent>
            </IonRow>
        </IonCol>
    );
};

export default ContactCellComponent;
