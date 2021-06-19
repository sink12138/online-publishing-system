using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using UploadFile;

namespace ArticleManager
{
    public partial class UpdatingArticleForm : Form
    {
        SqlConnection Sc;
        SqlDataAdapter Sda;
        DataSet Ds;
        DataTable Dt;
        int newID,newauthorid,rr;
        string newtitle,newcategory,newabstract,localName;
        public UpdatingArticleForm()
        {
            InitializeComponent();
        }

        public UpdatingArticleForm(int editID,string edittitle,string editauthor,string editcategory,string editabstract,int rownum)
        {
            InitializeComponent();
            rr = rownum;
            newID = editID;
            textBox1.Text = editID.ToString();
            textBox2.Text = edittitle;
            textBox4.Text = editauthor;
            textBox6.Text = editcategory;
            textBox3.Text = editabstract;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            button2.Enabled = false;
            SelectAuthor sa = new SelectAuthor();
            sa.ShowDialog();
            button2.Enabled = true;
            if (sa.operationSucceed)
            {
                textBox4.Text = sa.name;
            }
            newauthorid = sa.authorid;
        }

        private void button3_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.InitialDirectory = "c:\\";//注意这里写路径时要用c:\\而不是c:\
            openFileDialog.Filter = "(*.doc;*.docx)|*.doc;*.docx";
            openFileDialog.RestoreDirectory = true;
            openFileDialog.FilterIndex = 1;
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                localName = openFileDialog.FileName;
                textBox5.Text = localName;
            }
        }

        private void updateFile()
        {
            String id = newID.ToString();//此处为待更新文章id

            //删除原有文件
            Upload up = new Upload();
            String targetName1 = "a" + id + ".doc";
            String targetName2 = "a" + id + ".docx";
            up.delete_files(targetName1);
            up.delete_files(targetName2);

            String targetName = "a" + id + "." + localName.Substring(localName.LastIndexOf(".") + 1, (localName.Length - localName.LastIndexOf(".") - 1));
            up.transfer_files(localName, targetName);//上传文章
        }

        private void UpdatingArticleForm_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }

        private void textBox5_TextChanged(object sender, EventArgs e)
        {

        }

        private void updatecategory(int newID,string newcategory)
        {
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from CategoryRelationship", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("update CategoryRelationship set category=@category where articleid=@articleid", Sc);
            scmd.Parameters.Add(new SqlParameter("@articleid", SqlDbType.Int, 4, "articleid"));
            scmd.Parameters.Add(new SqlParameter("@category", SqlDbType.Text, 999999, "category"));
            Sda.UpdateCommand = scmd;
            DataRow dr = Dt.Rows[rr];
            try
            {
                dr["articleid"] = newID;
                dr["category"] = newcategory;
                Sda.Update(Ds);
            }
            catch { };
        }

        private void updatearticle(int newID,string newtitle,string newabstract)
        {
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from ActiveArticle", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("update ActiveArticle set title=@title,abstract=@abstract where articleid="+newID.ToString(), Sc);
            scmd.Parameters.Add(new SqlParameter("@title", SqlDbType.VarChar, 80, "title"));
            scmd.Parameters.Add(new SqlParameter("@abstract", SqlDbType.Text, 999999, "abstract"));
            Sda.UpdateCommand = scmd;
            DataRow dr = Dt.Rows[rr];
            try
            {
                dr["title"] = newtitle;
                dr["abstract"] = newabstract;
                Sda.Update(Ds);
            }
            catch { };
        }

        private void updateauthorrelationship(int newID,int newauthorid)
        {
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select * from AuthorRelationship", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            SqlCommand scmd = new SqlCommand("update AuthorRelationship set authorid=@authorid where articleid=@articleid", Sc);
            scmd.Parameters.Add(new SqlParameter("@articleid", SqlDbType.Int, 4, "articleid"));
            scmd.Parameters.Add(new SqlParameter("@authorid", SqlDbType.Int, 4, "authorid"));
            Sda.UpdateCommand = scmd;
            DataRow dr = Dt.Rows[rr];
            try
            {
                dr["articleid"] = newID;
                dr["authorid"] = newauthorid;
                Sda.Update(Ds);
            }
            catch { };
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if(textBox2.Text.ToString().Length==0 || textBox6.Text.ToString().Length == 0 || textBox3.Text.ToString().Length == 0)
            {
                MessageBox.Show("请将信息填写完整");
                return;
            }
            newtitle=textBox2.Text;
            newcategory=textBox6.Text;
            newabstract=textBox3.Text;
            updatearticle(newID, newtitle, newabstract);
            updatecategory(newID, newcategory);
            updateauthorrelationship(newID, newauthorid);
            if (textBox5.TextLength > 0)
                updateFile();
            this.Close();
        }
    }
}
