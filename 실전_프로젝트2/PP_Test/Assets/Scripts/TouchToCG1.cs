using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TouchToCG1 : MonoBehaviour {
    
    RaycastHit hit;
    private GameObject target;
    
    DialogueManager1 theDM;

    void Start() {
        theDM = FindObjectOfType<DialogueManager1>();
    }

    void Update() {

        if (Input.GetMouseButtonDown(0)) {
            target = GetClickedObject();

            if(!theDM.isDialogue && target.Equals(gameObject)) {
                theDM.ShowDialogue(hit.transform.GetComponent<InteractionEvent>().GetDialogue());
            }
        }
    }

    private GameObject GetClickedObject() {
        GameObject target = null;

        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition); //마우스 포인트 근처 좌표를 만든다. 

        if(true == (Physics.Raycast(ray.origin, ray.direction * 10, out hit))) {
            target = hit.collider.gameObject; 

        } 

        return target; 
    } 

}
