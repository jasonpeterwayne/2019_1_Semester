using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using UnityEngine.SceneManagement;

public class PlayerController : MonoBehaviour
{
    public int current;
    public int count = 0;
    public Slider ItemSlider;

    private Rigidbody rb;

    void Start() //Activate only once
    {
        rb = GetComponent<Rigidbody>(); //Get Player's Rigidbody, MonoBehaviour->GetComponent
        current = count; //초기 카운터는 0개 
    }

    void OnTriggerEnter(Collider other) //Player, Pick Up Tag
    {
        if (other.gameObject.CompareTag("Item")) //Pick Up과 부딪쳤을 경우
        {
            other.gameObject.SetActive(false); //부딪친 물체는 사라짐
            current += 10; //왼쪽 상단의 카운트 증가 
            ItemSlider.value = current;
            count++;
        }
    }
}