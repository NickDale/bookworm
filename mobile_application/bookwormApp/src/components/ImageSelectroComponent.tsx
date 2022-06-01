import {IonCol, IonIcon, IonRow, IonText} from "@ionic/react";
import {imagesSharp} from "ionicons/icons";
import React from "react";

interface IProps {
    onClick: (e: React.FormEvent<HTMLInputElement>) => void;
}

const ImageSelectorComponent: React.FC<IProps> = (props: IProps): JSX.Element => {

    const onSelection = (e: React.FormEvent<HTMLInputElement>): void => {
        props.onClick(e);
    }

    return (
        <IonRow>
            <IonCol size="6">
                <IonText>Kép feltöltése</IonText>
            </IonCol>
            <IonCol size="6">
                <input accept="image/*" style={{display: 'none'}} id="icon-button-file" type="file"
                       onChangeCapture={(e: React.FormEvent<HTMLInputElement>) => onSelection(e)}/>
                <label htmlFor="icon-button-file">
                    <IonIcon aria-label="upload picture" icon={imagesSharp}/>
                </label>
            </IonCol>
        </IonRow>
    );
}

export default ImageSelectorComponent;
