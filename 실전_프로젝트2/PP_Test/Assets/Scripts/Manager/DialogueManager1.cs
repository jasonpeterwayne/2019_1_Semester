using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using UnityEngine.EventSystems; 

public class DialogueManager1 : MonoBehaviour
{
    [SerializeField] GameObject JoyStick_UI;
    [SerializeField] GameObject DialogueBackground;

    [SerializeField] Text Name;
    [SerializeField] Text DialogueText;

    Dialogue[] dialogues;
    public bool isDialogue = false;
    bool isNext = false;
    int contextCount = 0;
    int lineCount = 0;

    [Header("Text Print Delay")]
    [SerializeField] float textDelay;

    PlayerController thePC;
    RaycastHit hit;
    private GameObject target;

    void Start() {
        thePC = FindObjectOfType<PlayerController>();
    }
    void Update() {
        if(isDialogue) {
            if(isNext) {
                if(Input.GetMouseButtonDown(0)) {
                    target = GetClickedObject();

                    if(!target.Equals(DialogueBackground)) {
                        isNext = false;
                        DialogueText.text = "";
                        if(++contextCount < dialogues[lineCount].contexts.Length) {
                            StartCoroutine(TypeWriter());
                        } else {
                            contextCount = 0;
                            if(++lineCount < dialogues.Length) {
                                if(lineCount == 12) {
                                    StartCoroutine(TypeWriter());
                                    EndDialogue();
                                } else if(lineCount == 13 && thePC.count < 10) {
                                    lineCount--;
                                    EndDialogue();
                                } else {
                                    StartCoroutine(TypeWriter());
                                }

                            } else {
                                EndDialogue();
                                SceneManager.LoadScene("Scene_game2");
                            }
                        }
                    }
                }
            }
        }
    }
    public void ShowDialogue(Dialogue[] p_dialogues) {
        isDialogue = true;
        Name.text = "";
        DialogueText.text = "";
        dialogues = p_dialogues;

        if(thePC.count == 1) lineCount = 13;
        StartCoroutine(TypeWriter());
    }

    void EndDialogue() {
        isDialogue = false;
        isNext = false;
        contextCount = 0;
        // lineCount = 0;
        dialogues = null;
        SettingUI(false);
    }

    IEnumerator TypeWriter() {
        SettingUI(true);
        string t_ReplaceText = dialogues[lineCount].contexts[contextCount];
        t_ReplaceText = t_ReplaceText.Replace("&", ",");

        Debug.Log(lineCount + ": " + contextCount + " -> " + thePC.count);
        Name.text = dialogues[lineCount].name;

        for(int i = 0; i < t_ReplaceText.Length; i++) {
            DialogueText.text += t_ReplaceText[i];
            yield return new WaitForSeconds(textDelay);
        }

        isNext = true;
        yield return null;
    }

    void SettingUI(bool p_flag) {
        DialogueBackground.SetActive(p_flag);
        JoyStick_UI.SetActive(!p_flag);
    }

    private GameObject GetClickedObject() {
        GameObject target = null;

        Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition); //마우스 포인트 근처 좌표를 만든다. 

        if(true == (Physics.Raycast(ray.origin, ray.direction * 10, out hit))) {
            target = hit.collider.gameObject;
            Debug.Log(target.name);

        } 

        return target; 
    } 
    
}
